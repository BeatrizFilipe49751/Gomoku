package daw.isel.pt.gomoku.controllers.hypermedia

import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.PlayIn
import daw.isel.pt.gomoku.controllers.models.PublicGameInfo
import daw.isel.pt.gomoku.controllers.routes.GameRoutes
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.domain.User

fun GameOut.toGameSiren(): Siren<GameOut> {
    val className = this::class.java.simpleName
    val userClassName = User::class.java.simpleName
    return Siren(
        cls = className,
        properties = this.getProperties(),
        entities = listOf(
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerBlack.toString()
                ),
            ),
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerWhite.toString()
                ),
            ),
            Entity(
                cls = className,
                rel = listOf("game"),
                href = GameRoutes.GET_GAME.putParameters(
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
                href = GameRoutes.PLAY.putParameters(key="gameId", value=this.gameId),
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
        properties = this.getProperties(),
        entities = listOf(
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerBlack.toString()
                ),
            ),
            Entity(
                cls = userClassName,
                rel = listOf("user"),
                href = UserRoutes.GET_USER.putParameters(
                    key= "userId",
                    value= this.playerWhite.toString()
                ),
            )
        ),
        actions = listOf(),
        links = listOf()
    )
}