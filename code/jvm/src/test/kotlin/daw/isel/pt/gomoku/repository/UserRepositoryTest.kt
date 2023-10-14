package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.repository.dataJDBI.UsersDataJDBI
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import java.util.UUID
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserRepositoryTest {

    @Test
    fun `create user successfully`() = runWithHandle {
        val repo = UsersDataJDBI(it)
        val userName = newTestUserName()
        val email = newTestEmail()
        val token = newToken()
        val newUser = repo.createUser(userName, email, token)
        assertNotNull(newUser)
    }

    @Test
    fun `get user successfully`() = runWithHandle {
        val repo = UsersDataJDBI(it)
        assertNotNull(repo.getUser(1))
    }

    @Test
    fun `get non existent user`() = runWithHandle {
        val repo = UsersDataJDBI(it)
        val newUser = repo.getUser(Int.MAX_VALUE)
        assertNull(newUser)
    }

    companion object {
        private fun runWithHandle(block: (Handle) -> Unit) = jdbi.useTransaction<Exception>(block)

        private fun newTestUserName() = "user-${abs(Random.nextLong())}"

        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"

        private fun newToken() = UUID.randomUUID().toString()

        private val jdbi = Jdbi.create(
            PGSimpleDataSource().apply {
                setURL(System.getenv("JDBC_DATABASE_URL"))
            }
        )
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())
    }
}