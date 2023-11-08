package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.models.LobbyIn
import daw.isel.pt.gomoku.controllers.models.UserInCreate
import daw.isel.pt.gomoku.controllers.models.UserInLogin
import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.routes.LobbyRoutes
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.domain.AuthUser

fun UserOut.toUserSiren(): Siren<UserOut> {
    val className = this::class.java.simpleName
    return Siren(
        cls = className,
        properties = this.getProperties(),
        entities = listOf(
            Entity(
                cls = className,
                rel = listOf("user") ,
                href = UserRoutes.GET_USER.putParameters("userId", this.userId.toString()),
            )
        ),
        actions = listOf(
            Action(
                name = "createUser",
                title = "Create a User",
                method = "POST",
                href = UserRoutes.CREATE_USER,
                fields = getFields(UserInCreate::class.java),
                ),
            Action(
                name = "login",
                title = "Login",
                method = "POST",
                href = UserRoutes.LOGIN,
                fields = getFields(UserInLogin::class.java),
            ),
            Action(
                name = "getLeaderBoards",
                title = "Get the leaderboards of gomoku game",
                method = "GET",
                href = UserRoutes.GET_LEADERBOARD,
                fields = listOf(),
            ),
        ),
        links = listOf()
    )
}

fun AuthUser.toAuthUserSiren(): Siren<AuthUser> {
    val className = this::class.java.simpleName
    return Siren(
        cls = className,
        properties = this.getProperties(),
        entities = listOf(
            Entity(
                cls = className,
                rel = listOf("user"),
                href = UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString()),
            ),
            Entity(
                cls = className,
                rel = listOf("user"),
                href = UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString()),
            )
        ),
        actions = listOf(
            Action(
                name="logout",
                title = "Logout",
                method = "POST",
                href = UserRoutes.LOGOUT,
                fields = listOf()
            ),
            Action(
                name = "createLobby",
                title = "Create a lobby for somebody to join in",
                method = "POST",
                href = LobbyRoutes.CREATE_LOBBY,
                fields = getFields(LobbyIn::class.java)
            ),
            Action(
                name = "getLobbies",
                title = "get all Available lobbies to join in",
                method = "GET",
                href = LobbyRoutes.GET_AVAILABLE_LOBBIES,
                fields = listOf(),
            ),
            Action(
                name = "joinLobby",
                title = "Join an existingLobby",
                method = "PUT",
                href = LobbyRoutes.JOIN_LOBBY.putParameters("lobbyId", "123"),
                fields = listOf(),
            ),
        ),
        links = listOf(
            Link(
                rel= listOf("leaderboard"),
                href = UserRoutes.GET_LEADERBOARD
            )
        )
    )
}


