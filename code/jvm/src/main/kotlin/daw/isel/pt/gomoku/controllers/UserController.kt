package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.models.UserIn
import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.controllers.utils.toUserOutWithToken
import daw.isel.pt.gomoku.services.UserServices
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userServices: UserServices) {

    @GetMapping(UserRoutes.GET_USER)
    fun getUser(@PathVariable id: Int): ResponseEntity<UserOut> =
        ResponseEntity
            .status(HttpStatus.OK) // to be defined
            .body(
                userServices.getUser(id).toUserOut()
            )

    @PostMapping(UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userIn: UserIn): ResponseEntity<UserOutWithToken> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(userIn.username, userIn.email).toUserOutWithToken()
            )

    @PostMapping(UserRoutes.CREATE_LOBBY)
    fun createLobby(request: HttpServletRequest, @PathVariable id: Int): ResponseEntity<Int> {
        val token: String = request.getHeader("Authorization").removePrefix("Bearer ")
        return  ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userServices.createLobby(id, token))
    }


}