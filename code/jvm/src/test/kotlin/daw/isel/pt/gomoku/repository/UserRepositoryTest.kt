package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.utils.TestUtils.newTestEmail
import daw.isel.pt.gomoku.utils.TestUtils.newTestUserName
import daw.isel.pt.gomoku.utils.TestUtils.newToken
import daw.isel.pt.gomoku.utils.TestUtils.runWithHandle
import daw.isel.pt.gomoku.repository.dataJDBI.UsersDataJDBI
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createUser
import kotlin.test.*

class UserRepositoryTest {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()

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
        val user = createUser()
        val repo = UsersDataJDBI(it)
        assertNotNull(repo.getUser(user.userId))
    }

    @Test
    fun `get non existent user`() = runWithHandle {
        val repo = UsersDataJDBI(it)
        val newUser = repo.getUser(Int.MAX_VALUE)
        assertNull(newUser)
    }

    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()

}