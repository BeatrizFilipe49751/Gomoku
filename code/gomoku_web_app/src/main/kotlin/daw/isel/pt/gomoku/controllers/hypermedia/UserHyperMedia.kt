package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.hypermedia.serialization.Serializer.getFields
import daw.isel.pt.gomoku.controllers.hypermedia.serialization.Serializer.getProperties
import daw.isel.pt.gomoku.controllers.models.LobbyIn
import daw.isel.pt.gomoku.controllers.models.UserInCreate
import daw.isel.pt.gomoku.controllers.models.UserInLogin
import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.routes.Routes
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.domain.AuthUser

fun UserOut.toUserSiren(): Siren<UserOut> {
    val className = this::class.java.simpleName
    return Siren(
        cls = className,
        properties = getProperties(this),
        entities = listOf(
            Entity(
                cls = className,
                rel = listOf("user") ,
                href = Routes.UserRoutes.GET_USER.putParameters("userId", this.userId.toString()),
            )
        ),
        actions = listOf(
            Action(
                name = "createUser",
                title = "Create a User",
                method = "POST",
                href = Routes.UserRoutes.CREATE_USER,
                fields = getFields(UserInCreate::class.java),
                ),
            Action(
                name = "login",
                title = "Login",
                method = "POST",
                href = Routes.UserRoutes.LOGIN,
                fields = getFields(UserInLogin::class.java),
            ),
        ),
        links = listOf(
            Link(
                rel = listOf("leaderboard"),
                href = Routes.UserRoutes.GET_LEADERBOARD,
            )
        )
    )
}

fun AuthUser.toAuthUserSiren(): Siren<AuthUser> {
    val className = this::class.java.simpleName
    return Siren(
        cls = className,
        properties = getProperties(this),
        entities = listOf(
            Entity(
                cls = className,
                rel = listOf("user"),
                href = Routes.UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString()),
            ),
            Entity(
                cls = className,
                rel = listOf("user"),
                href = Routes.UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString()),
            )
        ),
        actions = listOf(
            Action(
                name="logout",
                title = "Logout",
                method = "POST",
                href = Routes.UserRoutes.LOGOUT,
                fields = listOf()
            ),
            Action(
                name = "createLobby",
                title = "Create a lobby for somebody to join in",
                method = "POST",
                href = Routes.LobbyRoutes.CREATE_LOBBY,
                fields = getFields(LobbyIn::class.java)
            ),
            Action(
                name = "joinLobby",
                title = "Join an existingLobby",
                method = "PUT",
                href = Routes.LobbyRoutes.JOIN_LOBBY.putParameters("lobbyId", "123"),
                fields = listOf(),
            ),
        ),
        links = listOf(
            Link(
                rel= listOf("leaderboard"),
                href = Routes.UserRoutes.GET_LEADERBOARD
            ),
            Link(
                rel= listOf("lobbies"),
                href = Routes.LobbyRoutes.GET_AVAILABLE_LOBBIES,
            )
        )
    )
}


