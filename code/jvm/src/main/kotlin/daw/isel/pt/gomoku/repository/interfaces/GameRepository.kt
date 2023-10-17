package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.domain.game.Game

interface GameRepository {
    fun getGame(gameId: String): GameSerialized
    fun createGame(game: Game, playerBlack: Int, playerWhite: Int): GameSerialized

    fun updateGame(gameId: String): GameSerialized
}