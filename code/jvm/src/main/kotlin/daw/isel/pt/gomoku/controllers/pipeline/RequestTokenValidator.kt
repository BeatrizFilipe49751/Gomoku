package daw.isel.pt.gomoku.controllers.pipeline


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.services.UserServices
import org.springframework.stereotype.Component

@Component
class RequestTokenValidator(val userServices: UserServices) {
    fun processAuthorizationHeaderValue(authorizationValue: String?, userId: String): User? {
        if (authorizationValue == null || !userId.all { it.isDigit() }) {
            return null
        }
        val parts = authorizationValue.trim().split(" ")
        if (parts.size != 2) {
            return null
        }
        if (parts[0].lowercase() != SCHEME) {
            return null
        }
        return userServices.checkUserToken(parts[1], userId.toInt())
    }

    companion object {
        const val SCHEME = "bearer"
    }
}