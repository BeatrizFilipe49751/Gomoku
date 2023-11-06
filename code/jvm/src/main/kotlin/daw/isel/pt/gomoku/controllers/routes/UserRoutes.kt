package daw.isel.pt.gomoku.controllers.routes

object UserRoutes {
    const val GET_USER = "/users/{userId}"
    const val CREATE_USER= "/users"
    const val LOGIN = "/users/token"
    const val LOGOUT = "/users/logout"
    const val GET_LEADERBOARD = "/users/leaderboard"
}