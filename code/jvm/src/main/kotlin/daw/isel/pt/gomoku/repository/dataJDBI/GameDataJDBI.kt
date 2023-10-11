package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameDataJDBI(private val jdbi: Jdbi): GameRepository {
    override fun getGame(id: UUID): Game {
        TODO("Not yet implemented")
    }

    override fun createGame(name: String, playerWhite: User, playerBlack: User): UUID {
        TODO("Not yet implemented")
    }

    override fun updateGame(id: UUID): Game {
        TODO("Not yet implemented")
    }
}