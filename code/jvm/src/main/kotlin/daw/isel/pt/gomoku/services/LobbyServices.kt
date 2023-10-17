package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.AlreadyInLobbyException
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.LobbyErrorMessages
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import org.springframework.stereotype.Component

@Component
class LobbyServices(private val transactionManager: TransactionManager) {
    fun createLobby(userId: Int, name: String?): Lobby {
        return transactionManager.run {
            if(name.isNullOrEmpty()) throw InvalidCredentialsException("Invalid name for lobby")
            if(it.lobbyRepository.isNotInLobby(userId))
                it.lobbyRepository.createLobby(userId, name)
            else throw AlreadyInLobbyException(LobbyErrorMessages.USER_ALREADY_IN_LOBBY)
        }
    }

    fun getLobbies(): List<Lobby> {
        return transactionManager.run {
            it.lobbyRepository.getLobbies()
        }
    }

    fun joinLobby(userId: Int, lobbyId: Int): Boolean {
        return transactionManager.run {
            if(it.lobbyRepository.isNotInLobby(userId))
                it.lobbyRepository.joinLobby(lobbyId, userId)
            else throw AlreadyInLobbyException(LobbyErrorMessages.USER_ALREADY_IN_LOBBY)
        }
    }

    fun getLobby(lobbyId: Int): Lobby {
        return transactionManager.run {
            it.lobbyRepository.getLobby(lobbyId)
                ?: throw NotFoundException(LobbyErrorMessages.LOBBY_NOT_FOUND)
        }
    }
    fun deleteLobby(userId: Int, lobbyId: Int): Boolean{
        return transactionManager.run {
           if(it.lobbyRepository.getLobby(lobbyId) != null) {
               if(it.lobbyRepository.isLobbyAdmin(lobbyId, userId)) {
                   it.lobbyRepository.deleteLobby(lobbyId)
               } else {
                   it.lobbyRepository.quitLobby(lobbyId, userId)
               }
           } else throw NotFoundException(LobbyErrorMessages.LOBBY_NOT_FOUND)
        }
    }
}