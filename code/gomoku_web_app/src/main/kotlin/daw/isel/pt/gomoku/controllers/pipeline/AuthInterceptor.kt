package daw.isel.pt.gomoku.controllers.pipeline

import daw.isel.pt.gomoku.controllers.loggin.LoggerMessages
import daw.isel.pt.gomoku.services.exceptions.UnauthorizedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(private val tokenValidator: RequestTokenValidator): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info(LoggerMessages.AuthLoggerMessages.AUTH_INTERCEPTOR)

        val authInfo = getAuthToken(request) ?: throw UnauthorizedException("Unauthorized Access")

        val authenticatedUser = tokenValidator.processAuthorizationHeaderValue(authInfo)
        return if(authenticatedUser == null) throw UnauthorizedException("Unauthorized Access")
        else {
            AuthUserArgumentResolver.addUserTo(authenticatedUser, request)
            true
        }
    }

    private fun getAuthToken(request: HttpServletRequest): String? {
        val authorizationHeader: String? = request.getHeader(NAME_AUTHORIZATION_HEADER)

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_STRING)) {
            logger.info("AUTH INTERCEPTOR: Got token from Header")
            return authorizationHeader
        }

        request.cookies?.find { it.name == "auth" }?.let { cookie ->
            logger.info("AUTH INTERCEPTOR: Got token from HTTP cookie")
            return "$BEARER_STRING${cookie.value}"
        }

        return null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthInterceptor::class.java)
        const val NAME_AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_STRING = "Bearer "
    }
}