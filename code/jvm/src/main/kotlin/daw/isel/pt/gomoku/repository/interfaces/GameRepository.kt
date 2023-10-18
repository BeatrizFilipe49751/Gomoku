package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.controllers.models.GameInfo

interface GameRepository {
    fun getGame(gameId: String): GameSerialized?
    fun createGame(game: GameSerialized,gameNumber: Int, playerBlack: Int, playerWhite: Int): Boolean

    fun updateGame(game: GameSerialized): Boolean
    fun checkGameStarted(gameNumber: Int): GameSerialized?
    fun checkGameInfo(gameId : String): GameInfo?
}