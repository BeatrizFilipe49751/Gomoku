package daw.isel.pt.gomoku.controllers.routes

object GameRoutes {
    const val PLAY = "/users/game/play/{gameId}"
    const val GET_GAME= "/users/game/{gameId}"
    const val AUTH_NEEDED = "/users/game/**"
}