package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.springframework.stereotype.Component

@Component
class UserServices(val userRepo: UserRepository) {
    fun getUser(id: Int): User {
        TODO()
    }

    fun createUser(username: String): User {
        TODO()
    }
}