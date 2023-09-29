package daw.isel.pt.gomoku.controllers.utils

import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.domain.User

fun User.toUserOut(): UserOut{
    return UserOut(this.username)
}

fun User.toUserOutWithToken(): UserOutWithToken{
    return UserOutWithToken(this.username, this.token)
}
