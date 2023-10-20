package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.utils.TestUtils.newTestEmail
import daw.isel.pt.gomoku.utils.TestUtils.newTestUserName
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.newTestPassword
import daw.isel.pt.gomoku.utils.TestUtils.userServices
import kotlin.test.*

class UserServicesTest {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()
    @Test
    fun `create User successfully`() {
        val email = newTestEmail()
        val username = newTestUserName()
        val password = newTestPassword()
        val user = userServices.createUser(username, email, password)
        assertTrue { user.email == email && user.username == username  }
    }

    @Test
    fun `create user with with no email`() {
        val username = newTestUserName()
        assertFailsWith<InvalidCredentialsException> { userServices.createUser( username, null, null) }
    }

    @Test
    fun `create user with with no username`() {
        val email = newTestEmail()
        assertFailsWith<InvalidCredentialsException> { userServices.createUser( null, email, null) }
    }

    @Test
    fun `create user with with invalid email`() {
        val username = newTestUserName()
        val password = newTestPassword()
        val email = "wrong.com"
        assertFailsWith<InvalidCredentialsException> { userServices.createUser(username , email, password) }
    }
    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()
}