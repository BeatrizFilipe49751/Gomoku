package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserServices(val userRepo: UserRepository) {
    fun getUser(id: Int) = userRepo.getUser(id) ?: throw NotFoundException("User not found")
    fun createUser(username: String?, email: String?): User {
        if (username.isNullOrEmpty() || email.isNullOrEmpty()) throw InvalidCredentialsException("Username or email missing")
        val newToken = UUID.randomUUID().toString()
        return userRepo.createUser(username, email, newToken)
    }

    fun createLobby(userId: Int): Int {
        return userRepo.createLobby(userId)
    }
}