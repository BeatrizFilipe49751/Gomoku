package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.jdbi.v3.core.Handle


class GameDataJDBI(private val handle: Handle): GameRepository {
    override fun getGame(gameId: String): GameSerialized? {
        return handle.createQuery("SELECT gameid, name, board, state FROM games WHERE gameid = :id")
            .bind("id", gameId)
            .mapTo(GameSerialized::class.java)
            .singleOrNull()

    }

    override fun createGame(game: GameSerialized, playerBlack: Int, playerWhite: Int): GameSerialized {
        TODO()
    }

    override fun updateGame(gameId: String): GameSerialized {
        TODO("Not yet implemented")
    }
}