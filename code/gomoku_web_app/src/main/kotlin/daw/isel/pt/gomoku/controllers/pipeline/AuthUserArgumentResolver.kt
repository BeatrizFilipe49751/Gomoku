package daw.isel.pt.gomoku.controllers.pipeline

import daw.isel.pt.gomoku.domain.AuthUser
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Component
class AuthUserArgumentResolver : HandlerMethodArgumentResolver {

    // .parameterType not working
    override fun supportsParameter(parameter: MethodParameter) = parameter.parameterType == AuthUser::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?: throw IllegalStateException("Missing Servlet")
        return getUserFrom(request) ?: throw IllegalStateException("Missing User")
    }

    companion object {
        private const val KEY = "AuthUserArgumentResolver"

        fun addUserTo(user: AuthUser, request: HttpServletRequest) {
            return request.setAttribute(KEY, user)
        }

        fun getUserFrom(request: HttpServletRequest): AuthUser? {
            return request.getAttribute(KEY)?.let {
                it as? AuthUser
            }
        }
    }
}