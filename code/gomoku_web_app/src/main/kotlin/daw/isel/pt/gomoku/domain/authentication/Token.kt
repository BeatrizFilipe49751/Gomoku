package daw.isel.pt.gomoku.domain.authentication

import kotlinx.datetime.Instant

class Token (
    val tokenValidationInfo: TokenValidationInfo,
    val userId: Int,
    val createdAt: Instant,
    val lastUsedAt: Instant
)