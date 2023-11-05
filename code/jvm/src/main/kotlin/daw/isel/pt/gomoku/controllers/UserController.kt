package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toLoginSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toUserHyperMedia
import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userServices: UserServices) {
    @GetMapping(UserRoutes.GET_USER)
    fun getUser(@PathVariable userId: Int): ResponseEntity<Siren> =
        ResponseEntity
            .status(HttpStatus.OK) // to be defined
            .body(
                userServices.getUser(id = userId)
                    .toUserHyperMedia()
            )

    @PostMapping(UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userIn: UserInCreate): ResponseEntity<Siren> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(
                    username = userIn.username,
                    email = userIn.email,
                    password = userIn.password
                ).toUserHyperMedia()
            )

    @PostMapping(UserRoutes.CREATE_TOKEN)
    fun createToken(@RequestBody userInLogin: UserInLogin): ResponseEntity<Siren> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.createToken(
                    email = userInLogin.email,
                    password = userInLogin.password
                ).toLoginSiren()
            )

    @PostMapping(UserRoutes.LOGOUT)
    fun logout(authedUser : AuthUser): ResponseEntity<Siren>  {
        val loggedOut = userServices.removeToken(authedUser.token)
        val user = authedUser.user
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(user.toUserHyperMedia())
    }

    @GetMapping(UserRoutes.GET_LEADERBOARD)
    fun getLeaderboard(): ResponseEntity<List<UserPoints>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.getLeaderboard()
            )
}