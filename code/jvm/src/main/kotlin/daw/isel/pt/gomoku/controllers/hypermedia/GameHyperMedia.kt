package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.hypermedia.serialization.Serializer.getFields
import daw.isel.pt.gomoku.controllers.hypermedia.serialization.Serializer.getProperties
import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.PlayIn
import daw.isel.pt.gomoku.controllers.models.PublicGameInfo
import daw.isel.pt.gomoku.controllers.routes.Uris
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.domain.User

fun GameOut.toGameSiren(): Siren<GameOut> {
    val className = this::class.java.simpleName
    val userClassName = User::class.java.simpleName
    return Siren(
        cls = className,
        properties = getProperties(this),
        entities = listOf(
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = Uris.UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerBlack.toString()
                ),
            ),
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = Uris.UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerWhite.toString()
                ),
            ),
            Entity(
                cls = className,
                rel = listOf("game"),
                href = Uris.GameRoutes.GET_GAME.putParameters(
                    key= "gameId",
                    value= this.gameId,
                )
            )
        ),
        actions = listOf(
            Action(
                name= "play",
                title = "Play a piece into the board",
                method = "PUT",
                href = Uris.GameRoutes.PLAY.putParameters(key="gameId", value=this.gameId),
                fields = getFields(PlayIn::class.java)
            )
        ),
        links = listOf()
    )
}

fun PublicGameInfo.toGameInfoSiren(): Siren<PublicGameInfo> {
    val className = this::class.java.simpleName
    val userClassName = User::class.java.simpleName
    return Siren(
        cls = className,
        properties = getProperties(this),
        entities = listOf(
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = Uris.UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerBlack.toString()
                ),
            ),
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = Uris.UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerWhite.toString()
                ),
            )
        ),
        actions = listOf(),
        links = listOf()
    )
}