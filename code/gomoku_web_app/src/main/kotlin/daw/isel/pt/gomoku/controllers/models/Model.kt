package daw.isel.pt.gomoku.controllers.models

import daw.isel.pt.gomoku.domain.game.BOARD_DIM_MIN
import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.game.Opening
import daw.isel.pt.gomoku.domain.game.Variant

data class UserOut(val userId: Int, val username: String, val email: String)
data class UserInCreate(val username: String?, val email: String?, val password: String?)
data class UserInLogin(val email: String?, val password: String?)
data class LobbyIn(val name: String?, val opening: Int = Opening.FREESTYLE.id, val variant: Int = Variant.FREESTYLE.id, val size: Int = BOARD_DIM_MIN)
data class GameSerialized(val gameId: String, val name: String, val opening: Int, val variant: Int, val board: String, val state: Char, val turn: Char)
data class PlayIn(val row: Int, val col: Int)
data class LobbyOut(val lobbyId: Int, val p1: Int, val name: String)
data class ErrorResponse(val status: Int, val message: String = "No message provided")
data class GameOut(
    val gameId: String,
    val name: String,
    val opening: Int,
    val variant: Int,
    val boardSize: Int,
    val playerBlack: Int,
    val playerWhite: Int,
    val currentTurn: Char,
    val pieces: String,
    val state: String,

)
data class GameInfo(val game : String, val player_black : Int, val player_white : Int)
data class AllGameInfo(
    val game: Game,
    val gameInfo: GameInfo
)
data class PublicGameInfo(val name: String, val playerBlack : Int, val playerWhite: Int)
data class LobbyInfo(val message: String, val gameId: String?)
data class UserPoints(val userId: Int, val username: String, val points: Int)