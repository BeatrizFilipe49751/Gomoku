package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component


class UsersDataJDBI(private val handle: Handle): UserRepository {
    override fun getUser(id: Int): User? {
        return handle.createQuery("select id, username, token from users where id = :id")
            .bind("id", id)
            .mapTo(User::class.java)
            .singleOrNull()

    }


    override fun createUser(username: String, email: String, token: String): User {
        val id: Int = handle.createQuery(
                "INSERT INTO users (username, email, token) values (:username, :email, :token) RETURNING id")
                .bind("username", username)
                .bind("email", email)
                .bind("token", token)
                .mapTo(Int::class.java)
                .single()


        return User(id, username, token)
    }

    override fun createLobby(userId: Int): Int {
        return handle.createQuery("insert into lobby(p1, p2) values (:p1, null) RETURNING id")
                .bind("p1", userId)
                .mapTo(Int::class.java)
                .single()
    }

    override fun getLobbies(): List<Lobby> {
        TODO("Not yet implemented")
    }

    override fun joinLobby(): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkUserToken(token: String): User? {
        return handle.createQuery("select * from users where id = :id and token = :token")
                .bind("token", token)
                .mapTo(User::class.java)
                .singleOrNull()
    }


}