package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toAuthUserSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toUserSiren
import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.controllers.routes.Uris
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userServices: UserServices) {
    @GetMapping(Uris.UserRoutes.GET_USER)
    fun getUser(@PathVariable userId: Int): ResponseEntity<Siren<UserOut>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.getUser(id = userId)
                    .toUserOut()
                    .toUserSiren()
            )

    @PostMapping(Uris.UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userIn: UserInCreate): ResponseEntity<Siren<UserOut>> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(
                    username = userIn.username,
                    email = userIn.email,
                    password = userIn.password
                ).toUserOut()
                    .toUserSiren()
            )

    @PostMapping(Uris.UserRoutes.LOGIN)
    fun login(@RequestBody userInLogin: UserInLogin): ResponseEntity<Siren<AuthUser>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.login(
                    email = userInLogin.email,
                    password = userInLogin.password
                ).toAuthUserSiren()
            )

    @PostMapping(Uris.UserRoutes.LOGOUT)
    fun logout(authedUser : AuthUser): ResponseEntity<Siren<UserOut>>  {
        userServices.logout(authedUser.token)
        val user = authedUser.user.toUserOut()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(user.toUserSiren())
    }

    @GetMapping(Uris.UserRoutes.GET_LEADERBOARD)
    fun getLeaderboard(): ResponseEntity<List<UserPoints>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.getLeaderboard()
            )
}