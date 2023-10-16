package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.Lobby

interface LobbyRepository {
    fun createLobby(userId: Int): Int
    fun getLobby(userId: Int, lobbyId: Int): Lobby?
    fun getLobbies(): List<Lobby>
    fun quitLobby(lobbyId: Int): Boolean
    fun joinLobby(lobbyId: Int, userId: Int): Boolean
    fun isInLobby(userId: Int): Boolean
}