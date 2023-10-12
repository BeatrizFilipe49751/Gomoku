package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.UUID

@Component
class UserServices(val transactionManager: TransactionManager) {
    fun getUser(id: Int): User {
        return transactionManager.run {
            it.usersRepository.getUser(id) ?: throw NotFoundException("User not found")
        }
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
            if(it.usersRepository.isInLobby(userId))
                it.usersRepository.createLobby(userId)
            else throw Exception() // TODO
        }
    }

    fun getLobbies(): List<Lobby> {
        return transactionManager.run {
            it.usersRepository.getLobbies()
        }
    }

    fun joinLobby(userId: Int, lobbyId: Int): Boolean {
        return transactionManager.run {
            if(it.usersRepository.isInLobby(userId))
                it.usersRepository.joinLobby(userId, lobbyId)
            else throw Exception() // TODO
        }
    }

    fun checkUserToken(token: String): User? {
        return transactionManager.run {
            it.usersRepository.checkUserToken(token)
        }
    }
}