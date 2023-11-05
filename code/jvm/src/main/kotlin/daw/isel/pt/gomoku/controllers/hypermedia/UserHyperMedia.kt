package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.models.LobbyIn
import daw.isel.pt.gomoku.controllers.models.UserInCreate
import daw.isel.pt.gomoku.controllers.models.UserInLogin
import daw.isel.pt.gomoku.controllers.routes.LobbyRoutes
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.domain.User
fun User.toUserHyperMedia(): Siren {
    val className = this::class.java.simpleName
    return Siren(
        cls = className,
        properties = this.getProperties().filter {
            it.value == this.passwordValidation.validationInfo },
        entities = listOf(
            Entity(
                cls = className,
                rel = this::class.java.simpleName,
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
                href = UserRoutes.CREATE_TOKEN,
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
        links = listOf(
            Link(
                rel = "self",
                href = UserRoutes.GET_USER.putParameters("userId", this.userId.toString())
            ),
            Link(
                rel = "self",
                href = UserRoutes.GET_LEADERBOARD
            )
        )
    )
}

fun AuthUser.toLoginSiren(): Siren {
    val className = this::class.java.simpleName
    return Siren(
        cls = className,
        properties = this.getProperties(),
        entities = listOf(
            Entity(
                cls = className,
                rel = this::class.java.simpleName,
                href = UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString()),
            ),
            Entity(
                cls = className,
                rel = this::class.java.simpleName,
                href = UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString()),
            )
        ),
        actions = listOf(
            Action(
                name = "getLeaderBoards",
                title = "Get the leaderboards of gomoku game",
                method = "GET",
                href = UserRoutes.GET_LEADERBOARD,
                fields = listOf(),
            ),
            Action(
                name="logout",
                title = "Logout",
                method = "POST",
                href = UserRoutes.LOGOUT,
                fields = listOf()
            ),
            Action(
                name = "creatLobby",
                title = "Create a lobby for somebody to join in",
                method = "POST",
                href = LobbyRoutes.CREATE_LOBBY,
                fields = getFields(LobbyIn::class.java)
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
                rel = "self",
                href = UserRoutes.GET_USER.putParameters("userId", this.user.userId.toString())
            ),
        )
    )
}

fun Any.getProperties(): Map<String, String> {
    val declaredField = this::class.java.declaredFields
    val propertyMap: MutableMap<String, String> = mutableMapOf()
    declaredField.forEach { field ->
        if(field.trySetAccessible()){
            propertyMap[field.name] = field.get(this).toString()
        }
    }
    return propertyMap
}

fun getFields(cls: Class<*>): List<Field> {
    val constructor = cls.constructors[0]
    val list = mutableListOf<Field>()
    constructor.parameters.forEach { field ->
        list.add(
            Field(
                name = field.name,
                type = field.type.simpleName
            )
        )
    }
    return list
}


