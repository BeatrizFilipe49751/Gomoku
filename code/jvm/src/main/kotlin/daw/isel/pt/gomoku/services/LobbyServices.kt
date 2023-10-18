package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.*
import org.springframework.stereotype.Component

@Component
class LobbyServices(private val transactionManager: TransactionManager) {
    fun createLobby(userId: Int, name: String?): Lobby {
        return transactionManager.run {
            if(it.usersRepository.getUser(userId) == null)
                throw NotFoundException(UserErrorMessages.USER_NOT_FOUND)
            if(name.isNullOrEmpty())
                throw InvalidCredentialsException(LobbyErrorMessages.INVALID_NAME)
            if(it.lobbyRepository.isNotInLobby(userId))
                it.lobbyRepository.createLobby(
                    userId = userId,
                    name = name
                )
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
            if(it.usersRepository.getUser(userId) == null)
                throw NotFoundException(UserErrorMessages.USER_NOT_FOUND)
            if(it.lobbyRepository.isNotInLobby(userId))
                it.lobbyRepository.joinLobby(
                    lobbyId = lobbyId,
                    userId = userId
                )
            else throw AlreadyInLobbyException(LobbyErrorMessages.USER_ALREADY_IN_LOBBY)
        }
    }

    fun getLobby(lobbyId: Int): Lobby {
        return transactionManager.run {
            it.lobbyRepository.getLobby(lobbyId)
                ?: throw NotFoundException(LobbyErrorMessages.LOBBY_NOT_FOUND)
        }
    }

    /**
     * when is lobby admin, checks if there is another player in the lobby
     * if there isn't the lobby is deleted, if there is, the other player becomes that lobby's admin
     */
    fun deleteLobby(userId: Int, lobbyId: Int): Boolean{
        return transactionManager.run {
            if(it.usersRepository.getUser(userId) == null)
                throw NotFoundException(UserErrorMessages.USER_NOT_FOUND)
            val lobby = it.lobbyRepository.getLobby(lobbyId)
            if(lobby != null) {
                when(lobby.p1) {
                    userId -> {
                        when(lobby.p2) {
                            null ->  it.lobbyRepository.deleteLobby(lobbyId)
                            else ->  it.lobbyRepository.switchLobbyAdmin(
                                lobbyId = lobbyId,
                                userId = userId
                            )
                        }
                    } else -> it.lobbyRepository.quitLobby(
                                    lobbyId = lobbyId,
                                    userId = userId
                            )
                }
            } else throw NotFoundException(LobbyErrorMessages.LOBBY_NOT_FOUND)
        }
    }
}