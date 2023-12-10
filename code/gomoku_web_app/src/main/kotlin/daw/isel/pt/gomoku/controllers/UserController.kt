package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toAuthUserSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toUserSiren
import daw.isel.pt.gomoku.controllers.loggin.LoggerMessages
import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.controllers.routes.Routes
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.services.UserServices
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userServices: UserServices) {
    @GetMapping(Routes.UserRoutes.GET_USER)
    fun getUser(@PathVariable userId: Int): ResponseEntity<Siren<UserOut>> {
        logger.info(LoggerMessages.UserLoggerMessages.GET_USER)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.getUser(id = userId)
                    .toUserOut()
                    .toUserSiren()
            )
    }


    @PostMapping(Routes.UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userIn: UserInCreate): ResponseEntity<Siren<UserOut>> {
        logger.info(LoggerMessages.UserLoggerMessages.CREATE_USER)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(
                    username = userIn.username,
                    email = userIn.email,
                    password = userIn.password
                ).toUserOut()
                    .toUserSiren()
            )
    }


    @PostMapping(Routes.UserRoutes.LOGIN)
    fun login(@RequestBody userInLogin: UserInLogin): ResponseEntity<Siren<AuthUser>> {
        logger.info(LoggerMessages.UserLoggerMessages.LOGIN)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.login(
                    email = userInLogin.email,
                    password = userInLogin.password
                ).toAuthUserSiren()
            )
    }


    @PostMapping(Routes.UserRoutes.LOGOUT)
    fun logout(authedUser : AuthUser): ResponseEntity<Siren<UserOut>>  {
        logger.info(LoggerMessages.UserLoggerMessages.LOGOUT)

        userServices.logout(authedUser.token)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                authedUser.user
                    .toUserOut()
                    .toUserSiren()
            )
    }

    @GetMapping(Routes.UserRoutes.GET_LEADERBOARD)
    fun getLeaderboard(
        @RequestParam(name = "skip", defaultValue = "0") skip: Int,
        @RequestParam(name = "limit", defaultValue = "5") limit: Int
    ): ResponseEntity<List<UserPoints>> {
        logger.info(LoggerMessages.UserLoggerMessages.GET_LEADERBOARD)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.getLeaderboard(skip = skip, limit = limit)
            )
    }


    companion object{
        private val logger = LoggerFactory
            .getLogger(UserController::class.java)
    }
}