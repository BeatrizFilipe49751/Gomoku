package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.repository.interfaces.LobbyRepository
import org.jdbi.v3.core.Handle

class LobbyDataJDBI(private val handle: Handle): LobbyRepository {
    override fun createLobby(userId: Int): Int {
        return handle.createQuery("insert into lobby(p1, p2) values (:p1, null) RETURNING lobbyid")
            .bind("p1", userId)
            .mapTo(Int::class.java)
            .single()
    }

    override fun getLobbies(): List<Lobby> {
        return handle.createQuery("select * from lobby where p2 IS NULL")
            .mapTo(Lobby::class.java)
            .list()
    }

    override fun getLobby(userId: Int, lobbyId: Int): Lobby? {
        return handle.createQuery("select lobbyid, p1, p2 from lobby where lobbyid= :lobbyId")
            .bind("lobbyId", lobbyId)
            .mapTo(Lobby::class.java)
            .singleOrNull()
    }
    override fun quitLobby(lobbyId: Int): Boolean {
        val numRows = handle.createUpdate("DELETE FROM lobby where lobby.lobbyid = :lobbyId")
            .bind("lobbyId", lobbyId)
            .execute()
        return numRows > 0
    }

    override fun isInLobby(userId: Int): Boolean{
        val numLobbies = handle.createQuery("select count(*) from lobby where p1 = :userId OR p2 = :userId")
            .bind("userId", userId)
            .mapTo(Int::class.java)
            .single()
        return numLobbies < 1
    }

    override fun joinLobby(lobbyId: Int, userId: Int): Boolean {
        val numRows = handle.createUpdate("UPDATE lobby set p2 = :userId where lobbyid = :lobbyId")
            .bind("lobbyId", lobbyId)
            .bind("userId", userId)
            .execute()
        return numRows > 0
    }
}