package daw.isel.pt.gomoku.controllers

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LobbyControllerTest {
    @LocalServerPort
    var port: Int = 0

    @Test
    fun `create Lobby Successfully`() {

    }
}