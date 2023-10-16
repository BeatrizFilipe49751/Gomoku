package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.jdbi.v3.core.Handle


class GameDataJDBI(private val handle: Handle): GameRepository {
    override fun getGame(id: String): Game {
        TODO("Not yet implemented")
    }

    override fun createGame(gameId: String, name: String, playerWhite: User, playerBlack: User): Game {
        TODO()
    }

    override fun updateGame(id: String): Game {
        TODO("Not yet implemented")
    }
}