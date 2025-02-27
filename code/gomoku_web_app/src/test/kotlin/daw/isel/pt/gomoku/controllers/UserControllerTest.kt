package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.routes.Routes
import daw.isel.pt.gomoku.controllers.utils.putParameters
import daw.isel.pt.gomoku.services.exceptions.UserErrorMessages
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createNewClient
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.newTestEmail
import daw.isel.pt.gomoku.utils.TestUtils.newTestPassword
import daw.isel.pt.gomoku.utils.TestUtils.newTestUserName
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import kotlin.test.BeforeTest
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerTest {
    @LocalServerPort
    var port: Int = 0

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()
    @Test
    fun `Create User Successfully`() {
        val newClient = createNewClient(port)
        val userName = newTestUserName()
        val email = newTestEmail()
        val password = newTestPassword()
        newClient.post().uri(Routes.UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf(
                    "username" to userName,
                    "email" to email,
                    "password" to password
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            //.jsonPath("username").isEqualTo(userName)
            //.jsonPath("email").isEqualTo(email)
    }

    @Test
    fun `Create user without passing email`() {
        val newClient = createNewClient(port)
        val username = newTestUserName()
        newClient.post().uri(Routes.UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf("username" to username)
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("message").isEqualTo(UserErrorMessages.PARAMETERS_MISSING)
    }

    @Test
    fun `Create user without passing username`() {
        val newClient = createNewClient(port)
        val email = newTestEmail()
        newClient.post().uri(Routes.UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf("email" to email)
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("message").isEqualTo(UserErrorMessages.PARAMETERS_MISSING)
    }
    @Test
    fun `Create user with invalid email`() {
        val newClient = createNewClient(port)
        val userName = newTestUserName()
        val password = newTestPassword()
        val email = "WrongEmailFormat.com"
        newClient.post().uri(Routes.UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf(
                    "username" to userName,
                    "email" to email,
                    "password" to password
                )
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("message").isEqualTo(UserErrorMessages.EMAIL_WRONG_FORMAT)
    }

    @Test
    fun `Get user successfully`() {
        val user = createUserAndLogin()
        val path = Routes.UserRoutes.GET_USER.putParameters("userId", user.user.userId.toString())
        val newClient = createNewClient(port)
        newClient.get().uri(path)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            //.jsonPath("properties").isEqualTo(user.user.username)

    }

    @Test
    fun `Get user with Invalid id`() {
        
        val id = Int.MAX_VALUE
        Routes.UserRoutes.GET_USER.putParameters("userId", id.toString())
        val path = Routes.UserRoutes.GET_USER.putParameters("userId", id.toString())
        val newClient = createNewClient(port)
        newClient.get().uri(path)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("message").isEqualTo(UserErrorMessages.USER_NOT_FOUND)
    }

    @BeforeTest
    fun resetAgain() = TestUtils.resetDatabase()
}