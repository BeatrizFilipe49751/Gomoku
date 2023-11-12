package daw.isel.pt.gomoku.domain

import daw.isel.pt.gomoku.domain.authentication.PasswordValidationInfo

data class User (
        val userId: Int,
        val username: String,
        val email: String,
        val passwordValidation: PasswordValidationInfo
)