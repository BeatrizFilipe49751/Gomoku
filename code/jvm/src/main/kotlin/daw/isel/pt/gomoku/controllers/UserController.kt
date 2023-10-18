package daw.isel.pt.gomoku.controllers


import daw.isel.pt.gomoku.controllers.models.UserIn
import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userServices: UserServices) {

    @GetMapping(UserRoutes.GET_USER)
    fun getUser(@PathVariable userId: Int): ResponseEntity<UserOut> =
        ResponseEntity
            .status(HttpStatus.OK) // to be defined
            .body(
                userServices.getUser(id = userId).toUserOut()
            )

    @PostMapping(UserRoutes.CREATE_USER)
    fun createUser(@RequestBody userIn: UserIn): ResponseEntity<User> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                userServices.createUser(
                    username = userIn.username,
                    email = userIn.email
                )
            )
}