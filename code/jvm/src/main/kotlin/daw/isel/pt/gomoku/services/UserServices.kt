package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.TransactionManager
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import daw.isel.pt.gomoku.services.exceptions.UnauthorizedException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserServices(val transactionManager: TransactionManager) {
    fun getUser(id: Int): User =
        transactionManager.run {
            it.usersRepository.getUser(id) ?: throw NotFoundException("User not found")
        }

    fun createUser(username: String?, email: String?): User {
        if (username.isNullOrEmpty() || email.isNullOrEmpty()) throw InvalidCredentialsException("Username or email missing")
        if(!email.contains("@")) throw InvalidCredentialsException("Username or email missing")
        val newToken = UUID.randomUUID().toString()
        return transactionManager.run {
            it.usersRepository.createUser(username, email, newToken)
        }
    }

    fun createLobby(userId: Int): Int {
        return transactionManager.run {
            it.usersRepository.createLobby(userId)
        }
    }

    fun checkUserToken(token: String): User? {
        return transactionManager.run {
            it.usersRepository.checkUserToken(token)
        }

    }
}