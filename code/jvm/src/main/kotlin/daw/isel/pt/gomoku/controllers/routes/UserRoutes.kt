package daw.isel.pt.gomoku.controllers.routes

object UserRoutes {
    const val GET_USER = "/users/{id}"
    const val CREATE_USER="/users"
    const val CREATE_LOBBY = "/users/{id}/lobby"
    const val GET_AVAILABLE_LOBBIES = "/users/{id}/lobbies"
    const val JOIN_LOBBY = "/users/{userId}/lobby/{lobbyId}"
    const val AUTH_NEEDED = "/users/{id}/lobby/**"
}