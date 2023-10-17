package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.game.Game

interface GameRepository {
    fun getGame(gameId: String): Game
    fun createGame(game: Game, playerBlack: Int, playerWhite: Int): Game

    fun updateGame(gameId: String): Game
}