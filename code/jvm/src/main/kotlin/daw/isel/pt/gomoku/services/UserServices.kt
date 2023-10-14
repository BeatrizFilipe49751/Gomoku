package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.*
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserServices(private val transactionManager: TransactionManager) {
    fun getUser(id: Int): User {
        return transactionManager.run {
            it.usersRepository.getUser(id) ?: throw NotFoundException(UserErrorMessages.USER_NOT_FOUND)
        }
    }

    fun createUser(username: String?, email: String?): User {
        if (username.isNullOrEmpty() || email.isNullOrEmpty()) throw InvalidCredentialsException(UserErrorMessages.PARAMETERS_MISSING)
        if(!email.contains("@")) throw InvalidCredentialsException(UserErrorMessages.EMAIL_WRONG_FORMAT)
        val newToken = UUID.randomUUID().toString()
        return transactionManager.run {
            it.usersRepository.createUser(username, email, newToken)
        }
    }

    fun checkUserToken(token: String): User? {
        return transactionManager.run {
            it.usersRepository.checkUserToken(token)
        }
    }
}