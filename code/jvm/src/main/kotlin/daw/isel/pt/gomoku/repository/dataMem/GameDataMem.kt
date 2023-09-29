package daw.isel.pt.gomoku.repository.dataMem

import daw.isel.pt.gomoku.domain.Game
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameDataMem: GameRepository {
    override fun getGame(id: UUID): Game {
        TODO("Not yet implemented")
    }

    override fun createGame(name: String, playerWhite: User, playerBlack: User): UUID {
        TODO("Not yet implemented")
    }
}