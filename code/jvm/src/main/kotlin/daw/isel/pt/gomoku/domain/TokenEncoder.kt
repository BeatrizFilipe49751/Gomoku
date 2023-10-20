package daw.isel.pt.gomoku.domain

interface TokenEncoder {
    fun createValidationInformation(token: String): TokenValidationInfo
}