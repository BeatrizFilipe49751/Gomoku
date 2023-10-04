package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.jdbi.v3.core.Jdbi
import java.util.UUID

class UsersDataJDBI(private val jdbi: Jdbi): UserRepository {
    override fun getUser(id: Int): User? {
        return jdbi.withHandle<User?, Exception> { handle ->
            handle.createQuery("select id, username from users where id = :id")
                .bind("id", id)
                .mapTo(User::class.java)
                .singleOrNull()
        }
    }

    override fun createUser(username: String): User {
        val newToken = UUID.randomUUID().toString()
        return jdbi.withHandle<User?, Exception> { handle ->
            handle.createQuery("INSERT INTO users values (:username, :token)")
                .bind("username", username)
                .bind("token", newToken)
                .mapTo(User::class.java)
                .singleOrNull()
        }
    }
}