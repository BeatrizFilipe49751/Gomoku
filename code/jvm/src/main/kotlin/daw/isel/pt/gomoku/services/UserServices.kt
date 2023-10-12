package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import daw.isel.pt.gomoku.services.exceptions.UnauthorizedException
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

    fun createLobby(userId: Int, token: String): Int = checkUserToken(userId, token) {
        userRepo.createLobby(userId)
    }

    fun <T> checkUserToken(userId: Int, token: String, function: () -> T): T {
        val verify = userRepo.checkUserToken(userId, token)
        return if(verify != null) {
            function()
        } else {
            throw UnauthorizedException("Unauthorized access")
        }
    }

}