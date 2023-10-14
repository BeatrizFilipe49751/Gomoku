package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.repository.dataJDBI.transactions.JdbiTransactionManager
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class UserServicesTest {
    @Test
    fun `create User successfully`() {
        val email = newTestEmail()
        val username = newTestUserName()
        val user = services.createUser(username, email)
        assertTrue { user.email == email && user.username == username  }
    }

    @Test
    fun `create user with with no email`() {
        val username = newTestUserName()
        assertFailsWith<InvalidCredentialsException> { services.createUser( username, null) }
    }

    @Test
    fun `create user with with no username`() {
        val email = newTestEmail()
        assertFailsWith<InvalidCredentialsException> { services.createUser( null, email) }
    }

    @Test
    fun `create user with with invalid email`() {
        val username = newTestUserName()
        val email = "wrong.com"
        assertFailsWith<InvalidCredentialsException> { services.createUser(username , email) }
    }


    companion object {

        private fun newTestUserName() = "user-${abs(Random.nextLong())}"

        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"


        private val jdbi = Jdbi.create(
            PGSimpleDataSource().apply {
                setURL(System.getenv("JDBC_DATABASE_URL"))
            }
        )
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())

        val services = UserServices(JdbiTransactionManager(jdbi))
    }


}