package daw.isel.pt.gomoku.repository.dataJDBI.mappers

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.domain.authentication.PasswordValidationInfo
import java.sql.ResultSet

class UserMapper {
    fun mapResultSetToUser(rs: ResultSet): User {
        return User(
            userId = rs.getInt("userId"),
            username = rs.getString("username"),
            email = rs.getString("email"),
            passwordValidation = PasswordValidationInfo(
                validationInfo = rs.getString("password_validation")
            )
        )
    }
}