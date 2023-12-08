package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.controllers.models.GameInfo
import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.jdbi.v3.core.Handle


class GameDataJDBI(private val handle: Handle): GameRepository {
    override fun getGame(gameId: String): GameSerialized? {
        return handle.createQuery("SELECT gameId, name, opening, variant, board, state, turn FROM games WHERE gameid = :id")
            .bind("id", gameId)
            .mapTo(GameSerialized::class.java)
            .singleOrNull()
    }

    override fun createGame(game: GameSerialized, gameNumber: Int, playerBlack: Int, playerWhite: Int): Boolean {
        val numRowsGame = handle.createUpdate( """
            INSERT into games(gameid, board, name, opening, variant, state, turn)  
            VALUES (:gameId, :board, :name, :opening, :variant, :state, :turn)
        """.trimIndent()
        )
            .bind("gameId", game.gameId)
            .bind("board", game.board)
            .bind("name", game.name)
            .bind("opening", game.opening)
            .bind("variant", game.variant)
            .bind("state", game.state)
            .bind("turn", game.turn)
            .execute()

        val numRowsGameUsers = handle.createUpdate("""
             INSERT INTO game_users(game, game_number, player_black, player_white) 
             VALUES (:game, :gameNumber, :player_black, :player_white)
        """.trimIndent()

        )
            .bind("game", game.gameId)
            .bind("gameNumber", gameNumber)
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
                UPDATE games SET board = :board, state=:state, turn=:turn where gameid = :gameId
            """.trimIndent()
        )
            .bind("board", game.board)
            .bind("gameId", game.gameId)
            .bind("state", game.state)
            .bind("turn", game.turn)
            .execute()

        return numRows > 0
    }

    override fun checkGameStarted(gameNumber: Int): GameSerialized? {
        return handle.createQuery("""
             SELECT gameid, name, opening, variant, board, state, turn from games where gameid =
             (
                 SELECT game from game_users 
                 where  game_number = :gameNumber
             )
        """.trimIndent())
            .bind("gameNumber", gameNumber)
            .mapTo(GameSerialized::class.java)
            .singleOrNull()

    }
    override fun checkGameInfo(gameId: String): GameInfo? {
        return handle.createQuery("SELECT game, player_white, player_black FROM game_users WHERE game = :game")
            .bind("game", gameId)
            .mapTo(GameInfo::class.java)
            .singleOrNull()
    }

    override fun addUserToLeaderboard(userId: Int, username: String, points: Int): Boolean {
        val numRows = handle.createUpdate("""
             INSERT INTO leaderboard(userId, username, points) 
             VALUES (:userId, :username, :points)
        """.trimIndent()
        )
            .bind("userId", userId)
            .bind("username", username)
            .bind("points", points)
            .execute()

        return numRows > 0
    }

    override fun addUserPoints(userId: Int, points: Int): Boolean {
        val numRows = handle.createUpdate(
            """
                UPDATE leaderboard SET points = (points + :points) where userId = :userId
            """.trimIndent()
        )
            .bind("points", points)
            .bind("userId", userId)
            .execute()

        return numRows > 0
    }

    override fun getLeaderboardUserId(userId: Int): Int? {
        return handle.createQuery("SELECT userId FROM leaderboard WHERE userId = :userId")
            .bind("userId", userId)
            .mapTo(Int::class.java)
            .singleOrNull()
    }
}