package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.dataJDBI.transactions.JdbiTransactionManager
import daw.isel.pt.gomoku.services.LobbyServices
import daw.isel.pt.gomoku.services.UserServices
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
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
        val user = createUser()
        val lobbyName = newLobbyName()
        val uri = "/users/${user.userId}/lobby"
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
            .jsonPath("p1").isEqualTo(user.userId)
    }

    @Test
    fun `create Lobby without token`() {
        val client = createNewClient(port)
        val user = createUser()
        val lobbyName = newLobbyName()
        val uri = "/users/${user.userId}/lobby"
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
        val user = createUser()
        val uri = "/users/${user.userId}/lobby"
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
        val user = createUser()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/${user.userId}/lobby/${lobby.lobbyId}"
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
        val user = createUser()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/${user.userId}/lobby/${lobby.lobbyId}"
        client.get().uri(uri)
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `get non-existentLobby`() {
        val user = createUser()
        val client = createNewClient(port)
        val uri = "/users/${user.userId}/lobby/${Int.MAX_VALUE}"
        client.get().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `join lobby successfully`() {
        val user = createUser()
        val otherUser = createUser()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/${otherUser.userId}/lobby/${lobby.lobbyId}"
        client.put().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("name").isEqualTo(lobby.name)
            .jsonPath("gameId").exists()
    }

    @Test
    fun `join lobby unauthorized`() {
        val user = createUser()
        val otherUser = createUser()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/${otherUser.userId}/lobby/${lobby.lobbyId}"
        client.put().uri(uri)
            .exchange()
            .expectStatus().isCreated
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `p1 quits lobby and p2 becomes lobby admin`() {
        val user = createUser()
        val otherUser = createUser()
        val lobby = createLobby(user)
        val client = createNewClient(port)
        val uri = "/users/${otherUser.userId}/lobby/${lobby.lobbyId}"
        client.delete().uri(uri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.token)
            .exchange()
            .expectStatus().isCreated
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("status").isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }


    companion object {
        private val jdbi = Jdbi.create(
            PGSimpleDataSource().apply {
                setURL(System.getenv("JDBC_DATABASE_URL"))
            }
        )
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())

        fun createUser() = userServices.createUser(
            username = newTestUserName(),
            email = newTestEmail(),
        )

        fun createLobby(user: User): Lobby {
            return lobbyServices
                .createLobby(
                    userId = user.userId,
                    name = newLobbyName()
                )
        }
        private val userServices = UserServices(JdbiTransactionManager(jdbi))
        private val lobbyServices = LobbyServices(JdbiTransactionManager(jdbi))
        private fun newLobbyName() = "lobby-${abs(Random.nextLong())}"
        private fun newTestUserName() = "user-${abs(Random.nextLong())}"
        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"
        private fun createNewClient(port: Int): WebTestClient {
            return WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        }
    }
}