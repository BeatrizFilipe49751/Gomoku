package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.Lobby

interface LobbyRepository {
    fun createLobby(userId: Int): Lobby
    fun getLobby(userId: Int, lobbyId: Int): Lobby?
    fun getLobbies(): List<Lobby>
    fun quitLobby(lobbyId: Int): Boolean
    fun joinLobby(lobbyId: Int, userId: Int): Boolean
    fun isNotInLobby(userId: Int): Boolean
}