package daw.isel.pt.gomoku.controllers.pipeline


import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.services.UserServices
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RequestTokenValidator(val userServices: UserServices) {
    fun processAuthorizationHeaderValue(authorizationValue: String?): AuthUser? {
        if (authorizationValue == null) {
            return null
        }
        val parts = authorizationValue.trim().split(" ")
        if (parts.size != 2) {
            logger.info("FAILED IN SIZE SIZE: ${parts.size}")
            return null
        }
        if (parts[0].lowercase() != SCHEME) {
            logger.info("FAILED IN SCHEME")
            return null
        }
        return userServices.checkUserToken(parts[1])
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RequestTokenValidator::class.java)
        const val SCHEME = "bearer"
    }
}