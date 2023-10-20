package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.utils.TestUtils.runWithHandle
import daw.isel.pt.gomoku.repository.dataJDBI.UsersDataJDBI
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import kotlin.test.*

class UserRepositoryTest {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()

    @Test
    fun `get user successfully`() = runWithHandle {
        val user = createUserAndLogin()
        val repo = UsersDataJDBI(it)
        assertNotNull(repo.getUser(user.user.userId))
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