package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createLobby
import daw.isel.pt.gomoku.utils.TestUtils.createNewClient
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.joinLobby
import daw.isel.pt.gomoku.utils.TestUtils.newLobbyName
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LobbyControllerTest {
    @LocalServerPort
    var port: Int = 0

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()

    @Test
    fun `create Lobby Successfully`() {
        val client = createNewClient(port)
        val user = createUserAndLogin()
        val lobbyName = newLobbyName()
        val uri = "/users/lobby"
        client.post().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .bodyValue(
                mapOf(
                    "name" to lobbyName
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("name").isEqualTo(lobbyName)
            .jsonPath("p1").isEqualTo(user.user.userId)
    }

    @Test
    fun `create Lobby without token`() {
        val client = createNewClient(port)
        val user = createUserAndLogin()
        val lobbyName = newLobbyName()
        val uri = "/users/lobby"
        client.post().uri(uri)
            .bodyValue(
                mapOf(
                    "name" to lobbyName
                )
            )
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `create Lobby without name`() {
        val client = createNewClient(port)
        val user = createUserAndLogin()
        val uri = "/users/lobby"
        client.post().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .bodyValue(
                mapOf(
                    "name" to ""
                )
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `createLobby and get it`() {
        val user = createUserAndLogin()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/lobby/${lobby.lobbyId}"
        client.get().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("lobbyId").isEqualTo(lobby.lobbyId)
            .jsonPath("name").isEqualTo(lobby.name)
            .jsonPath("p1").isEqualTo(lobby.p1)
    }

    @Test
    fun `createLobby and get it without token when getting it`() {
        val user = createUserAndLogin()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/lobby/${lobby.lobbyId}"
        client.get().uri(uri)
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `get non-existentLobby`() {
        val user = createUserAndLogin()
        val client = createNewClient(port)
        val uri = "/users/lobby/${Int.MAX_VALUE}"
        client.get().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `join lobby successfully`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/lobby/${lobby.lobbyId}"
        client.put().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + otherUser.token)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("name").isEqualTo(lobby.name)
            .jsonPath("id").exists()
    }

    @Test
    fun `join lobby unauthorized`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/lobby/${lobby.lobbyId}"
        client.put().uri(uri)
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `p1 quits lobby and p2 becomes lobby admin`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = createLobby(user)
        joinLobby(otherUser, lobby)
        val client = createNewClient(port)
        val uri = "/users/lobby/${lobby.lobbyId}"
        client.delete().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("p1").isEqualTo(otherUser.user.userId)
    }

    @Test
    fun `p2 quits lobby`() {
        val user =  createUserAndLogin()
        val otherUser =  createUserAndLogin()
        val lobby =  createLobby(user)
        joinLobby(otherUser, lobby)
        val client =  createNewClient(port)
        val uri = "/users/lobby/${lobby.lobbyId}"
        client.delete().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + otherUser.token)
            .exchange()
            .expectStatus().isOk
            .expectBody()
    }

    @AfterTest
    fun resetAgain() {
        TestUtils.resetDatabase()
    }
}