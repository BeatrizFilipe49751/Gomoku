package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.controllers.models.TurnInfo

interface GameRepository {
    fun getGame(gameId: String): GameSerialized?
    fun createGame(game: GameSerialized, playerBlack: Int, playerWhite: Int): Boolean

    fun updateGame(game: GameSerialized): Boolean

    fun checkTurn(userId : Int, turn : Char, gameId : String): TurnInfo?
}