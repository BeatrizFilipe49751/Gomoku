package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.controllers.utils.toUserOutWithToken
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userServices: UserServices) {

    @GetMapping(UserRoutes.GETU_USER)
    fun getUser(@PathVariable id: Int): ResponseEntity<UserOut> {
        return ResponseEntity
            .status(HttpStatus.OK) // to be defined
            .body(
                userServices.getUser(id).toUserOut()
            )
    }

    @PostMapping(UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userName: String): ResponseEntity<UserOutWithToken> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(userName).toUserOutWithToken()
            )

    }
}