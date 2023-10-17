package daw.isel.pt.gomoku.controllers.models

data class UserOut(val userId: Int, val username: String, val email: String)
data class UserOutWithToken(val userId: Int, val username: String, val token: String)
data class UserIn(val username: String?, val email: String?)

data class LobbyIn(val name: String?)
data class ErrorResponse(val status: Int, val message: String = "No message provided")