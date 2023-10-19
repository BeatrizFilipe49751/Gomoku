package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.repository.interfaces.LobbyRepository
import org.jdbi.v3.core.Handle

class LobbyDataJDBI(private val handle: Handle): LobbyRepository {
    override fun createLobby(userId: Int, name: String): Lobby {
        val lobbyId = handle.createQuery("insert into lobby(p1, name, p2) values (:p1,:name, null) RETURNING lobbyId")
            .bind("p1", userId)
            .bind("name", name)
            .mapTo(Int::class.java)
            .single()

        return Lobby(lobbyId, name, userId, null)
    }

    override fun getLobbies(): List<Lobby> {
        return handle.createQuery("select * from lobby where p2 IS NULL")
            .mapTo(Lobby::class.java)
            .list()
    }

    override fun getLobby(lobbyId: Int): Lobby? {
        return handle.createQuery("select lobbyId,name, p1, p2 from lobby where lobbyId= :lobbyId")
            .bind("lobbyId", lobbyId)
            .mapTo(Lobby::class.java)
            .singleOrNull()
    }
    override fun deleteLobby(lobbyId: Int): Boolean {
        val numRows = handle.createUpdate("DELETE FROM lobby where lobby.lobbyId = :lobbyId")
            .bind("lobbyId", lobbyId)
            .execute()
        return numRows > 0
    }

    override fun quitLobby(lobbyId: Int, userId: Int): Boolean {
        val rows = handle.createUpdate("""
           UPDATE lobby SET p2 = NULL WHERE lobbyId = :lobbyId AND p2 = :userId
        """)
            .bind("lobbyId", lobbyId)
            .bind("userId", userId)
            .execute()
        return rows > 0
    }

    override fun isNotInLobby(userId: Int): Boolean{
        val numLobbies = handle.createQuery("select count(*) from lobby where p1 = :userId OR p2 = :userId")
            .bind("userId", userId)
            .mapTo(Int::class.java)
            .single()
        return numLobbies < 1
    }

    override fun switchLobbyAdmin(lobbyId: Int, userId: Int): Boolean {
        val numRows = handle.createUpdate("""
              UPDATE lobby SET p1 = p2, p2 = NULL WHERE lobbyId = :lobbyId AND p1 = :userId
        """)
            .bind("lobbyId", lobbyId)
            .bind("userId", userId)
            .execute()
        return numRows > 0
    }
    override fun joinLobby(lobbyId: Int, userId: Int): Boolean {
        val numRows = handle.createUpdate("UPDATE lobby set p2 = :userId where lobbyId = :lobbyId")
            .bind("lobbyId", lobbyId)
            .bind("userId", userId)
            .execute()
        return numRows > 0
    }
}