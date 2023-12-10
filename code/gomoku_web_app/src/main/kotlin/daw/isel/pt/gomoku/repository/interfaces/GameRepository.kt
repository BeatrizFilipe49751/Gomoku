package daw.isel.pt.gomoku.repository.interfaces

import daw.isel.pt.gomoku.controllers.models.GameInfo
import daw.isel.pt.gomoku.controllers.models.GameSerialized

interface GameRepository {
    fun getGame(gameId: String): GameSerialized?
    fun createGame(game: GameSerialized, gameNumber: Int, playerBlack: Int, playerWhite: Int): Boolean
    fun updateGame(game: GameSerialized): Boolean

    fun getUserActiveGame(userId: Int): String?
    fun checkGameStarted(gameNumber: Int): GameSerialized?
    fun checkGameInfo(gameId : String): GameInfo?
    fun addUserToLeaderboard(userId: Int, username: String, points: Int): Boolean
    fun addUserPoints(userId: Int, points: Int): Boolean
    fun getLeaderboardUserId(userId: Int): Int?
}