package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.controllers.models.TurnInfo
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.jdbi.v3.core.Handle


class GameDataJDBI(private val handle: Handle): GameRepository {
    override fun getGame(gameId: String): GameSerialized? {
        return handle.createQuery("SELECT gameId, name, board, state, turn FROM games WHERE gameid = :id")
            .bind("id", gameId)
            .mapTo(GameSerialized::class.java)
            .singleOrNull()
    }

    override fun createGame(game: GameSerialized, playerBlack: Int, playerWhite: Int): Boolean {
        val numRowsGame = handle.createUpdate( """
            INSERT into games(gameid, board, name, state, turn)  
            VALUES (:gameId, :board, :name, :state, :turn)
        """.trimIndent()
        )
            .bind("gameId", game.gameId)
            .bind("board", game.board)
            .bind("name", game.name)
            .bind("state", game.state)
            .bind("turn", game.turn)
            .execute()

        val numRowsGameUsers = handle.createUpdate("""
             INSERT INTO game_users(game, player_white, player_black) 
             VALUES (:game, :player_black, :player_white)
        """.trimIndent()

        )
            .bind("game", game.gameId)
            .bind("player_black", playerBlack)
            .bind("player_white", playerWhite)
            .execute()
        val insertedGame = numRowsGame > 0
        val insertedGameUsers = numRowsGameUsers > 0
        return insertedGame && insertedGameUsers

    }

    override fun updateGame(game: GameSerialized): Boolean {
        val numRows = handle.createUpdate(
            """
                UPDATE games SET board = :board where gameid = :gameId
            """.trimIndent()
        )
            .bind("board", game.board)
            .bind("gameId", game.gameId)
            .execute()

        return numRows > 0
    }

    override fun checkTurn(gameId: String): TurnInfo? {
        return handle.createQuery("SELECT gameId, player_white, player_black FROM game_users WHERE game = :game")
            .bind("game", gameId)
            .mapTo(TurnInfo::class.java)
            .singleOrNull()
    }
}