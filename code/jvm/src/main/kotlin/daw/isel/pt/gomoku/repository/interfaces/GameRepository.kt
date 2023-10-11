package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.User
import java.util.*

interface GameRepository {
    fun getGame(id: UUID): Game
    fun createGame(name: String, playerWhite: User, playerBlack: User): UUID

    fun updateGame(id: UUID): Game
}