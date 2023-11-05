package daw.isel.pt.gomoku.controllers


import daw.isel.pt.gomoku.controllers.hypermedia.HyperMedia
import daw.isel.pt.gomoku.controllers.hypermedia.toUserHyperMedia
import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userServices: UserServices) {

    @GetMapping(UserRoutes.GET_USER)
    fun getUser(@PathVariable userId: Int): ResponseEntity<HyperMedia> =
        ResponseEntity
            .status(HttpStatus.OK) // to be defined
            .body(
                userServices.getUser(id = userId)
                    .toUserHyperMedia(UserRoutes.GET_USER)
            )

    @PostMapping(UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userIn: UserInCreate): ResponseEntity<HyperMedia> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(
                    username = userIn.username,
                    email = userIn.email,
                    password = userIn.password
                ).toUserHyperMedia(UserRoutes.CREATE_USER)
            )

    @PostMapping(UserRoutes.CREATE_TOKEN)
    fun createToken(@RequestBody userInLogin: UserInLogin): ResponseEntity<TokenCreationResult> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                userServices.createToken(
                    email = userInLogin.email,
                    password = userInLogin.password
                )
            )

    @PostMapping(UserRoutes.LOGOUT)
    fun logout(user : AuthUser): ResponseEntity<Boolean>  {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userServices.removeToken(user.token))
    }


}