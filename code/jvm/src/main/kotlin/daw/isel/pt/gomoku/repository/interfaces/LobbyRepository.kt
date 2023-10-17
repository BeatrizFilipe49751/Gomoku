package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.Lobby

interface LobbyRepository {
    fun createLobby(userId: Int, name: String): Lobby
    fun getLobby(lobbyId: Int): Lobby?
    fun getLobbies(): List<Lobby>
    fun deleteLobby(lobbyId: Int): Boolean
    fun quitLobby(lobbyId: Int, userId: Int): Boolean
    fun joinLobby(lobbyId: Int, userId: Int): Boolean
    fun isLobbyAdmin(lobbyId: Int, userId: Int): Boolean
    fun isNotInLobby(userId: Int): Boolean
}