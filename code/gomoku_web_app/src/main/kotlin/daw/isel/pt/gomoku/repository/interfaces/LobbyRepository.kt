package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.Lobby

interface LobbyRepository {
    fun createLobby(
        userId: Int,
        name: String,
        opening: Int,
        variant: Int,
        boardSize: Int
    ): Lobby
    fun getLobby(lobbyId: Int): Lobby?

    fun getLobbyByUserId(userId: Int): Lobby?
    fun getLobbies(skip: Int, limit: Int): List<Lobby>

    fun getNumLobbies(): Int
    fun deleteLobby(lobbyId: Int): Boolean
    fun quitLobby(lobbyId: Int, userId: Int): Boolean
    fun joinLobby(lobbyId: Int, userId: Int): Boolean
    fun switchLobbyAdmin(lobbyId: Int, userId: Int): Boolean
    fun isNotInLobby(userId: Int): Boolean
}