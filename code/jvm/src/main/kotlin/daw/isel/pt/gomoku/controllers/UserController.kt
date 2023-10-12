package daw.isel.pt.gomoku.controllers


import daw.isel.pt.gomoku.controllers.models.UserIn
import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.controllers.utils.toUserOutWithToken
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.http.HttpStatus
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
    fun createLobby(@PathVariable id: Int): ResponseEntity<Int> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userServices.createLobby(id))
    }

    @GetMapping(UserRoutes.GET_AVAILABLE_LOBBIES)
    fun getLobbies(@PathVariable id: Int): ResponseEntity<List<Lobby>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userServices.getLobbies())
    }

    @PutMapping(UserRoutes.JOIN_LOBBY)
    fun joinLobby(@PathVariable userId: Int, @PathVariable lobbyId: Int): ResponseEntity<Boolean> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userServices.joinLobby(userId, lobbyId))
    }
}