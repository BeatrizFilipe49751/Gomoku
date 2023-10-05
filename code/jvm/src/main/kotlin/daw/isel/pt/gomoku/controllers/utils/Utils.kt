package daw.isel.pt.gomoku.controllers.utils

import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.domain.User

fun User.toUserOut(): UserOut{
    return UserOut(this.id, this.username)
}

fun User.toUserOutWithToken(): UserOutWithToken{
    return UserOutWithToken(this.id, this.username, this.token)
}
