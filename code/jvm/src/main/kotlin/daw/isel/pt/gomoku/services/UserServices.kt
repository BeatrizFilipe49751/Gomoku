package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserServices(val userRepo: UserRepository) {
    fun getUser(id: Int): User {
        TODO()
    }

    fun createUser(username: String, email: String): User {
        val newToken = UUID.randomUUID().toString()
        return userRepo.createUser(username, email, newToken)
    }
}