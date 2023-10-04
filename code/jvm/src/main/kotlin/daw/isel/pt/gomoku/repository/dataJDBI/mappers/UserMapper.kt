package daw.isel.pt.gomoku.repository.dataJDBI.mappers

import daw.isel.pt.gomoku.domain.User
import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class UserMapper: ColumnMapper<User> {
    override fun map(r: ResultSet?, columnNumber: Int, ctx: StatementContext?): User {
        TODO()
    }
}