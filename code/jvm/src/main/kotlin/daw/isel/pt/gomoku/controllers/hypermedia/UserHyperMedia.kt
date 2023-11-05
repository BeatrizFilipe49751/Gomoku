package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.models.UserInCreate
import daw.isel.pt.gomoku.controllers.models.UserInLogin
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.domain.User
fun User.toUserHyperMedia(path: String): HyperMedia {
    val className = this::class.java.simpleName
    return HyperMedia(
        cls = className,
        properties = this.getProperties(),
        entities = listOf(
            Entity(
                cls = className,
                rel = this::class.java.simpleName,
                href = UserRoutes.GET_USER,
            )
        ),
        actions = listOf(
            Action(
                name = "create_user",
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
                name = "logout",
                title = "Logout",
                method = "POST",
                href = UserRoutes.LOGOUT,
                fields = getFields(AuthUser::class.java)
            ),
            Action(
                name = "get_user",
                title = "Get user",
                method = "GET",
                href = UserRoutes.GET_USER,
                fields = listOf(
                    Field(
                        name = "userId",
                        type = "Int"
                    )
                ),
            )
        ),
        links = listOf(
            Link(
                rel = "self",
                href = path
            )
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


