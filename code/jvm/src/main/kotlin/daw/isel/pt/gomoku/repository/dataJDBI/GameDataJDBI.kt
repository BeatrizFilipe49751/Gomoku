package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import java.util.*


class GameDataJDBI(private val handle: Handle): GameRepository {
    override fun getGame(id: String): Game {
        TODO("Not yet implemented")
    }

    override fun createGame(id: String, name: String, playerWhite: User, playerBlack: User): Game {

    }

    override fun updateGame(id: String): Game {
        TODO("Not yet implemented")
    }
}