package daw.isel.pt.gomoku.controllers.config

import daw.isel.pt.gomoku.controllers.pipeline.AuthInterceptor
import daw.isel.pt.gomoku.controllers.pipeline.AuthUserArgumentResolver
import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class UserConfig(private val authInterceptor: AuthInterceptor, val authUserArgumentResolver: AuthUserArgumentResolver): WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor).addPathPatterns(UserRoutes.AUTH_NEEDED)
        registry.addInterceptor(authInterceptor).addPathPatterns(UserRoutes.LOGOUT)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authUserArgumentResolver)
    }
}