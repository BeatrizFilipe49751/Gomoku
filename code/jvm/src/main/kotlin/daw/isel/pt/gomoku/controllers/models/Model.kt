package daw.isel.pt.gomoku.controllers.models

data class UserOut(val username: String)

data class UserOutWithToken(val username: String, val token: String)
data class UserIn(val username: String)