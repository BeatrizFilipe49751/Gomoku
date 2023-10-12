package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.routes.UserRoutes

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
    var port: Int = 8080
    @Test
    fun `Create User Successfully`() {
        val newClient = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
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