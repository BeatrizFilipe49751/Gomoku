package daw.isel.pt.gomoku.controllers.models

import daw.isel.pt.gomoku.domain.game.Board

data class UserOut(val userId: Int, val username: String, val email: String)
data class UserOutWithToken(val userId: Int, val username: String, val token: String)
data class UserIn(val username: String?, val email: String?)

data class LobbyIn(val name: String?)

data class GameSerialized(val id: String, val name: String, val board: String, val state: Char)
data class ErrorResponse(val status: Int, val message: String = "No message provided")