package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.controllers.utils.toUserOut
import daw.isel.pt.gomoku.controllers.utils.toUserOutWithToken
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.print.attribute.standard.RequestingUserName

@RestController
class UserController(val userServices: UserServices) {

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: Int): UserOut {
        return userServices.getUser(id).toUserOut()
    }

    @PostMapping("/users")
    fun createUser(@RequestBody userName: String): UserOutWithToken {
        return userServices.createUser(userName).toUserOutWithToken()
    }
}