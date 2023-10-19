package daw.isel.pt.gomoku.controllers.models

data class UserOut(val userId: Int, val username: String, val email: String)
data class UserIn(val username: String?, val email: String?)

data class LobbyIn(val name: String?)

data class GameSerialized(val gameId: String, val name: String, val board: String, val state: Char, val turn: Char)
data class PlayIn(val gameId: String, val row: Int, val col: Int)

data class LobbyOut(val lobbyId: Int, val p1: Int, val name: String)

data class ErrorResponse(val status: Int, val message: String = "No message provided")
data class GameOut(val id: String, val name: String, val currentTurn: Char, val state: String)

data class GameInfo(val game : String, val player_black : Int, val player_white : Int)
