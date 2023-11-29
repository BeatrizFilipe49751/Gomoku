package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.hypermedia.serialization.Serializer.getProperties
import daw.isel.pt.gomoku.controllers.models.LobbyOut
import daw.isel.pt.gomoku.controllers.routes.Uris
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.domain.AuthUser

fun LobbyOut.toLobbySiren(authUser: AuthUser): Siren<LobbyOut> {
    val className = this::class.java.simpleName
    val userClassName = authUser.user::class.java.simpleName
    return Siren(
        cls = className,
        properties = getProperties(this),
        entities = listOf(
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = Uris.UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= authUser.user.userId.toString()
                ),
            ),
            Entity(
                cls = className,
                rel = listOf("lobby"),
                href = Uris.LobbyRoutes.GET_LOBBY.putParameters(
                    key= "lobbyId",
                    value= this.lobbyId.toString()
                )
            )
        ),
        actions = listOf(
            Action(
                name= "logout",
                title = "Logout of your account",
                method = "POST",
                href = Uris.UserRoutes.LOGOUT,
                fields = listOf()
            ),
            Action(
                name= "checkFullLobby",
                title = "Check if your lobby is filled and your game has started",
                method = "POST",
                href = Uris.LobbyRoutes.CHECK_FULL_LOBBY.putParameters(
                    key = "lobbyId", value = this.lobbyId.toString()
                ),
                fields = listOf()
            )
        ),
        links = listOf(
            Link(
                rel= listOf("leaderboard"),
                href = Uris.UserRoutes.GET_LEADERBOARD,
            ),
        ),
    )
}
