package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class UsersDataJDBI(private val jdbi: Jdbi): UserRepository {
    override fun getUser(id: Int): User? {
        return jdbi.withHandle<User?, Exception> { handle ->
            handle.createQuery("select id, username from users where id = :id")
                .bind("id", id)
                .mapTo(User::class.java)
                .singleOrNull()
        }
    }

    override fun createUser(username: String, email: String, token: String): User {
        val id = jdbi.withHandle<Int, Exception> { handle ->
            handle.createQuery(
                "INSERT INTO users(username, email, token) values (:username,:email,  :token) RETURNING id")
                .bind("username", username)
                .bind("email", email)
                .bind("token", token)
                .mapTo(Int::class.java)
                .singleOrNull()
        }
        return User(
            id, username, token
        )
    }
}