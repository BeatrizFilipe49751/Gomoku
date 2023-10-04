package daw.isel.pt.gomoku.controllers.models

import org.springframework.web.bind.annotation.RequestMethod

data class Request(val path: String, val method: RequestMethod)
data class UserOut(val username: String)
data class UserOutWithToken(val username: String, val token: String)
data class UserIn(val username: String, val token: String)