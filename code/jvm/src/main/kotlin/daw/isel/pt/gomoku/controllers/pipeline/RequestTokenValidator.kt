package daw.isel.pt.gomoku.controllers.pipeline

import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.controllers.models.UserWithToken
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.stereotype.Component

@Component
class RequestTokenValidator(val userServices: UserServices) {
    fun processAuthorizationHeaderValue(authorizationValue: String?): User? {
        if (authorizationValue == null) {
            return null
        }
        val parts = authorizationValue.trim().split(" ")
        if (parts.size != 2) {
            return null
        }
        if (parts[0].lowercase() != SCHEME) {
            return null
        }
        return userServices.checkUserToken(parts[1])
    }

    companion object {
        const val SCHEME = "bearer"
    }
}