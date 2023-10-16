package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.User
import java.util.*

interface GameRepository {
    fun getGame(gameId: String): Game
    fun createGame(gameId: String, name: String, playerWhite: User, playerBlack: User): Game

    fun updateGame(gameId: String): Game
}