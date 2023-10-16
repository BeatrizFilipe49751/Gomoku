package daw.isel.pt.gomoku.controllers.routes

object UserRoutes {
    const val GET_USER = "/users/{userId}"
    const val CREATE_USER="/users"
    const val AUTH_NEEDED = "/users/{userId}/lobby/**"
}