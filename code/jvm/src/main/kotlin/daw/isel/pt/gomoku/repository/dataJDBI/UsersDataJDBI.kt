package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.jdbi.v3.core.Handle


class UsersDataJDBI(private val handle: Handle): UserRepository {
    override fun getUser(userId: Int): User? {
        return handle.createQuery("select userId, username, email, token from users where userId = :userId")
            .bind("userId", userId)
            .mapTo(User::class.java)
            .singleOrNull()
    }


    override fun createUser(username: String, email: String, token: String): User {
        val userId: Int = handle.createQuery(
                "INSERT INTO users (username, email, token) values (:username, :email, :token) RETURNING userId")
                .bind("username", username)
                .bind("email", email)
                .bind("token", token)
                .mapTo(Int::class.java)
                .single()
        return User(userId, username, email, token)
    }

    override fun checkUserToken(token: String, userId: Int): User? {
        return handle.createQuery("select * from users where token = :token and userid = :userId")
            .bind("token", token)
            .bind("userId", userId)
            .mapTo(User::class.java)
            .singleOrNull()
    }


}