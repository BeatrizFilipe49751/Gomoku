package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.controllers.models.UserPoints
import daw.isel.pt.gomoku.domain.PasswordValidationInfo
import daw.isel.pt.gomoku.domain.Token
import daw.isel.pt.gomoku.domain.TokenValidationInfo
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.dataJDBI.mappers.TokenMapper
import daw.isel.pt.gomoku.repository.dataJDBI.mappers.UserMapper
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import kotlinx.datetime.Instant
import org.jdbi.v3.core.Handle


class UsersDataJDBI(private val handle: Handle): UserRepository {

    override fun getUser(userId: Int): User? {
        return handle.createQuery("select * from users where userId = :userId")
            .bind("userId", userId)
            .mapTo(User::class.java)
            .singleOrNull()
    }

    override fun getUserByEmail(email: String) : User? {
        return handle.createQuery("select * from users where email = :email")
            .bind("email", email)
            .mapTo(User::class.java)
            .singleOrNull()
    }

    override fun getUsername(userId: Int): String {
        return handle.createQuery("SELECT username FROM users WHERE userid = :userId")
            .bind("userId", userId)
            .mapTo(String::class.java)
            .single()
    }

    override fun createUser(username: String, email: String, passwordValidation: PasswordValidationInfo): User {
        val userId: Int = handle.createQuery(
                "INSERT INTO users (username, email, password_validation) values (:username, :email, :password_validation) RETURNING userId")
                .bind("username", username)
                .bind("email", email)
                .bind("password_validation", passwordValidation.validationInfo)
                .mapTo(Int::class.java)
                .single()
        return User(
            userId = userId,
            username = username,
            email = email,
            passwordValidation = passwordValidation
        )
    }


    override fun createToken(token: Token, maxTokens : Int) {
        handle.createUpdate(
            """
            delete from tokens 
            where user_id = :user_id 
                and token_validation in (
                    select token_validation from tokens where user_id = :user_id 
                        order by last_used_at desc offset :offset
                )
            """.trimIndent()
        )
            .bind("user_id", token.userId)
            .bind("offset", maxTokens - 1)
            .execute()

        handle.createUpdate(
            """
                insert into tokens(user_id, token_validation, created_at, last_used_at) values (:user_id, :token_validation, :created_at, :last_used_at)
            """.trimIndent()
        )
            .bind("user_id", token.userId)
            .bind("token_validation", token.tokenValidationInfo.validationInfo)
            .bind("created_at", token.createdAt.epochSeconds)
            .bind("last_used_at", token.lastUsedAt.epochSeconds)
            .execute()
    }

    override fun removeToken(token: TokenValidationInfo): Int {
        return handle.createUpdate(
            """
                delete from tokens
                where token_validation = :validation_information
            """
        )
            .bind("validation_information", token.validationInfo)
            .execute()
    }



    override fun checkUserToken(tokenValidationInfo: TokenValidationInfo): Pair<User, Token>? {
        return handle.createQuery(
            """
                select userId, username, email, password_validation, token_validation, created_at, last_used_at
                from users 
                inner join tokens 
                on users.userId = tokens.user_id
                where token_validation = :validation_information
            """
        )
            .bind("validation_information", tokenValidationInfo.validationInfo)
            .map { rs, _ ->
                UserMapper().mapResultSetToUser(rs) to TokenMapper().mapResultSetToToken(rs)
            }
            .singleOrNull()
    }

    override fun updateToken(token: Token, now: Instant) {
        handle.createUpdate(
            """
                update tokens
                set last_used_at = :last_used_at
                where token_validation = :validation_information
            """.trimIndent()
        )
            .bind("last_used_at", now.epochSeconds)
            .bind("validation_information", token.tokenValidationInfo.validationInfo)
            .execute()
    }

    override fun getLeaderboard(): List<UserPoints> {
        return handle.createQuery("select * from leaderboard group by username order by points desc")
            .mapTo(UserPoints::class.java)
            .list()
    }
}