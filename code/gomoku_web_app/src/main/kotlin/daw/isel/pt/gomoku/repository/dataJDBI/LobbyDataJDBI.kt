package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.repository.interfaces.LobbyRepository
import org.jdbi.v3.core.Handle

class LobbyDataJDBI(private val handle: Handle): LobbyRepository {
    override fun createLobby(
        userId: Int,
        name: String,
        opening: Int,
        variant: Int,
        boardSize: Int
    ): Lobby {
        val lobbyId = handle.createQuery("""
            insert into lobby(p1,p2,name, opening, variant, boardSize)
            values (:p1, null, :name, :opening, :variant, :boardSize) RETURNING lobbyId
        """.trimIndent())
            .bind("p1", userId)
            .bind("name", name)
            .bind("opening", opening)
            .bind("variant", variant)
            .bind("boardSize", boardSize)
            .mapTo(Int::class.java)
            .single()
        return Lobby(
            lobbyId= lobbyId,
            name= name,
            opening= opening,
            variant= variant,
            p1=userId,
            p2=null,
            boardSize = boardSize
        )
    }

    override fun getLobbies(skip: Int, limit: Int): List<Lobby> {
        return handle.createQuery("""
            select lobbyId, name, opening, variant, boardsize, p1 
            from lobby where p2 IS NULL
            LIMIT :limit
            OFFSET :skip
        """.trimIndent())
            .bind("limit", limit)
            .bind("skip", skip)
            .mapTo(Lobby::class.java)
            .list()
    }

    override fun getNumLobbies(): Int {
        return handle.createQuery("""
            select count(*) 
            from lobby where p2 IS NULL 
        """.trimIndent())
            .mapTo(Int::class.java)
            .single()
    }

    override fun getLobby(lobbyId: Int): Lobby? {
        return handle.createQuery("""
            select lobbyId, name, opening, variant, boardsize, p1, p2 from lobby where lobbyId= :lobbyId
        """.trimIndent())
            .bind("lobbyId", lobbyId)
            .mapTo(Lobby::class.java)
            .singleOrNull()
    }
    override fun getLobbyByUserId(userId: Int): Lobby? {
        return handle.createQuery("""
            select lobbyId, name, opening, variant, boardsize, p1, p2 from lobby where p1 = :userId
        """.trimIndent())
            .bind("userId", userId)
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