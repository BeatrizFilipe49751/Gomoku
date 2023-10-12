package daw.isel.pt.gomoku.controllers.routes

object UserRoutes {
    const val GET_USER = "/users/{id}"
    const val CREATE_USER="/users"
    const val CREATE_LOBBY = "/users/{id}/lobby"
    const val JOIN_LOBBY = "/users/lobby/{id}"
    const val AVAILABLE_LOBBIES = "/users/lobby"
    const val AUTH_NEEDED = "/users/{id}/**"
}