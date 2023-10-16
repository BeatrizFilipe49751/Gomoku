package daw.isel.pt.gomoku.controllers.pipeline

import daw.isel.pt.gomoku.services.exceptions.UnauthorizedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(private val tokenValidator: RequestTokenValidator): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authorizationHeader: String? = request.getHeader(NAME_AUTHORIZATION_HEADER)
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_STRING)) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            throw UnauthorizedException("Unauthorized Access")
        }
        logger.info("Handling Auth interceptor")
        val authenticatedUser = tokenValidator.processAuthorizationHeaderValue(authorizationHeader)
        return if(authenticatedUser == null) throw UnauthorizedException("Unauthorized Access")
        else true
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthInterceptor::class.java)
        const val NAME_AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_STRING = "Bearer "
    }
}