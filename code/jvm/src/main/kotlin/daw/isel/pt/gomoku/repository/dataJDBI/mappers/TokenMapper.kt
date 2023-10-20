package daw.isel.pt.gomoku.repository.dataJDBI.mappers

import daw.isel.pt.gomoku.domain.Token
import daw.isel.pt.gomoku.domain.TokenValidationInfo
import kotlinx.datetime.Instant
import java.sql.ResultSet

class TokenMapper {
    fun mapResultSetToToken(rs: ResultSet, ): Token {
        return Token(
            tokenValidationInfo = TokenValidationInfo(
                validationInfo = rs.getString("token_validation")
            ),
            userId = rs.getInt("userId"),
            createdAt = Instant.fromEpochSeconds(rs.getLong("created_at")),
            lastUsedAt = Instant.fromEpochSeconds(rs.getLong("last_used_at"))
        )
    }
}