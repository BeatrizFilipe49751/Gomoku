package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.routes.UserRoutes
import daw.isel.pt.gomoku.services.exceptions.UserErrorMessages

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.math.abs
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerTest {
    @LocalServerPort
    var port: Int = 0
    @Test
    fun `Create User Successfully`() {
        val newClient = createNewClient(port)
        val userName = newTestUserName()
        val email = newTestEmail()
        newClient.post().uri(UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf(
                    "username" to userName,
                    "email" to email
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("username").isEqualTo(userName)
            .jsonPath("email").isEqualTo(email)
    }

    @Test
    fun `Create user without passing email`() {
        val newClient = createNewClient(port)
        val username = newTestUserName()
        newClient.post().uri(UserRoutes.CREATE_USER)
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
        newClient.post().uri(UserRoutes.CREATE_USER)
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
        val email = "WrongEmailFormat.com"
        newClient.post().uri(UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf(
                    "username" to userName,
                    "email" to email
                )
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("message").isEqualTo(UserErrorMessages.EMAIL_WRONG_FORMAT)
    }

    @Test
    fun `Get user successfully`() {
        val id = 1
        val path = "/users/$id"
        val newClient = createNewClient(port)
        newClient.get().uri(path)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("username").isEqualTo("Dummy")

    }

    @Test
    fun `Get user with Invalid id`() {
        val id = Int.MAX_VALUE
        val path = "/users/$id"
        val newClient = createNewClient(port)
        newClient.get().uri(path)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("message").isEqualTo(UserErrorMessages.USER_NOT_FOUND)
    }

    @Test
    fun `Create lobby successfully`() {
        val newClient = createNewClient(port)
        val userName = newTestUserName()
        val email = newTestEmail()
        newClient.post().uri(UserRoutes.CREATE_USER)
            .bodyValue(
                mapOf(
                    "username" to userName,
                    "email" to email
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("username").isEqualTo(userName)
            .jsonPath("email").isEqualTo(email)
    }
    companion object {
        private fun newTestUserName() = "user-${abs(Random.nextLong())}"
        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"
        private fun createNewClient(port: Int): WebTestClient {
            return WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        }
    }
}