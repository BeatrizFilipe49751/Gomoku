package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.jdbi.v3.core.Handle


class UsersDataJDBI(private val handle: Handle): UserRepository {
    override fun getUser(id: Int): User? {
        return handle.createQuery("select id, username, token from users where id = :id")
            .bind("id", id)
            .mapTo(User::class.java)
            .singleOrNull()
    }


    override fun createUser(username: String, email: String, token: String): User {
        val id: Int = handle.createQuery(
                "INSERT INTO users (username, email, token) values (:username, :email, :token) RETURNING id")
                .bind("username", username)
                .bind("email", email)
                .bind("token", token)
                .mapTo(Int::class.java)
                .single()
        return User(id, username, token)
    }

    override fun quitLobby(lobbyId: Int): Boolean {
        val numRows = handle.createUpdate("DELETE FROM lobby where lobby.id = :lobbyId")
            .bind("lobbyId", lobbyId)
            .execute()
        return numRows > 0
    }

    override fun createLobby(userId: Int): Int {
        return handle.createQuery("insert into lobby(p1, p2) values (:p1, null) RETURNING id")
                .bind("p1", userId)
                .mapTo(Int::class.java)
                .single()
    }

    override fun checkUserToken(token: String): User? {
        return handle.createQuery("select * from users where id = :id and token = :token")
            .bind("token", token)
            .mapTo(User::class.java)
            .singleOrNull()
    }

    override fun isInLobby(userId: Int): Boolean{
        val numLobbies = handle.createQuery("select count(*) from lobby where p1 = :userId OR p2 = :userId")
            .bind("userId", userId)
            .mapTo(Int::class.java)
            .single()
        return numLobbies < 1
    }
    override fun getLobbies(): List<Lobby> {
        return handle.createQuery("select * from lobby where p2 IS NULL")
                .mapTo(Lobby::class.java)
                .list()
    }

    override fun joinLobby(lobbyId: Int, userId: Int): Boolean {
        val numRows = handle.createUpdate("UPDATE lobby set p2 = :userId where id = :lobbyId")
            .bind("lobbyId", lobbyId)
            .bind("userId", userId)
            .execute()
        return numRows > 0
    }
}