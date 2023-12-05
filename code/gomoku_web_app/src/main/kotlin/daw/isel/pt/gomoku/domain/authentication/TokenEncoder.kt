package daw.isel.pt.gomoku.domain.authentication

interface TokenEncoder {
    fun createValidationInformation(token: String): TokenValidationInfo
}