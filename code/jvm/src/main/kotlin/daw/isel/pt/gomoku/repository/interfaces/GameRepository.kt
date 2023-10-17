package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.controllers.models.GameSerialized

interface GameRepository {
    fun getGame(gameId: String): GameSerialized?
    fun createGame(game: GameSerialized, playerBlack: Int, playerWhite: Int): GameSerialized?

    fun updateGame(gameId: String): GameSerialized
}