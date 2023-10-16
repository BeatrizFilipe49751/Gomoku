package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.routes.LobbyRoutes
import daw.isel.pt.gomoku.domain.Lobby
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LobbyControllerTest {
    @LocalServerPort
    var port: Int = 0

    @Test
    fun `create Lobby Successfully`() {
        val client = createNewClient(port)
        client.post().uri(LobbyRoutes.CREATE_LOBBY)
    }

    companion object {
        private fun newTestUserName() = "user-${abs(Random.nextLong())}"
        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"
        private fun createNewClient(port: Int): WebTestClient {
            return WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        }
    }
}