package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toGameSiren
import daw.isel.pt.gomoku.controllers.loggin.LoggerMessages
import daw.isel.pt.gomoku.controllers.models.GameIdOut
import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.PlayIn
import daw.isel.pt.gomoku.controllers.routes.Routes
import daw.isel.pt.gomoku.controllers.utils.gameString
import daw.isel.pt.gomoku.controllers.utils.toGameOut
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.services.GameServices
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GameController(val gameServices: GameServices) {
    @PutMapping(Routes.GameRoutes.PLAY)
    fun play(
        authUser: AuthUser,
        @PathVariable gameId: String,
        @RequestBody playIn: PlayIn
    ): ResponseEntity<Siren<GameOut>> {
        logger.info(LoggerMessages.GameLoggerMessages.PLAY)

        val user = authUser.user
        val game = gameServices.getGame(
            gameId = gameId
        )
        val newGame = gameServices.play(
            game = game,
            userId = user.userId,
            row = playIn.row,
            col = playIn.col
        )
        logger.info("\n" + newGame.game.gameString())
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                newGame.toGameOut().toGameSiren()
            )
    }

    @PutMapping(Routes.GameRoutes.QUIT_GAME)
    fun quitGame(
        authUser: AuthUser,
        @PathVariable gameId: String
    ): ResponseEntity<Siren<GameOut>> {
        logger.info(LoggerMessages.GameLoggerMessages.QUIT_GAME)
        val game = gameServices.getGame(
            gameId = gameId
        )
        val newGame = gameServices.quitGame(
            game = game
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                newGame.toGameOut().toGameSiren()
            )
    }

    @GetMapping(Routes.GameRoutes.GET_GAME)
    fun getGame(authUser: AuthUser, @PathVariable gameId: String):
            ResponseEntity<Siren<GameOut>> {
        logger.info(LoggerMessages.GameLoggerMessages.GET_GAME)

        val game = gameServices.getGameInfo(
            gameId = gameId
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                game.toGameOut().toGameSiren()
            )
    }

    @GetMapping(Routes.GameRoutes.GET_USER_ACTIVE_GAME)
    fun getGameByUserId(authUser: AuthUser):
            ResponseEntity<GameIdOut> {
        logger.info(LoggerMessages.GameLoggerMessages.GET_USER_ACTIVE_GAME)

        val gameId = gameServices.getUserActiveGame(
            authUser.user.userId
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                GameIdOut(gameId)
            )
    }

    /*
    @GetMapping(Routes.GameRoutes.GET_GAME_PUBLIC)
    fun getGame(authUser: AuthUser, @PathVariable gameId: String):
            ResponseEntity<Siren<PublicGameInfo>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                gameServices.getGameInfo(gameId)
                .toGameInfoSiren()
            )
    }
    */

    companion object{
        private val logger = LoggerFactory.getLogger(GameController::class.java)
    }
}