package daw.isel.pt.gomoku.controllers.models

import org.springframework.web.bind.annotation.RequestMethod

data class UserOut(val id: Int, val username: String)
data class UserOutWithToken(val id: Int, val username: String, val token: String)
data class UserIn(val username: String, val email: String)

data class LobbyIn(val name: String, )