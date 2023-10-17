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

    override fun createGame(game: GameSerialized, playerBlack: Int, playerWhite: Int): Boolean {
        val numRowsGame = handle.createUpdate( """
            INSERT into games(gameid, board, name, state)  
            VALUES (:gameId, :board, :name, :state)
        """.trimIndent()
        )
            .bind("gameId", game.id)
            .bind("board", game.board)
            .bind("name", game.name)
            .bind("state", game.state)
            .execute()

        val numRowsGameUsers = handle.createUpdate("""
             INSERT INTO game_users(game, player_white, player_black) 
             VALUES (:game, :player_black, :player_white)
        """.trimIndent()

        )
            .bind("game", game.id)
            .bind("player_black", playerBlack)
            .bind("player_white", playerWhite)
            .execute()
        val insertedGame = numRowsGame > 0
        val insertedGameUsers = numRowsGameUsers > 0
        return insertedGame && insertedGameUsers

    }

    override fun updateGame(gameId: String): GameSerialized {
        TODO("Not yet implemented")
    }
}