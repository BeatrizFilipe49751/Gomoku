package daw.isel.pt.gomoku.services.exceptions

object UserErrorMessages {
    const val USER_NOT_FOUND = "User not found"
    const val PARAMETERS_MISSING = "Username, email or password missing"
    const val EMAIL_WRONG_FORMAT = "Email doesnt have @"
    const val INSECURE_PASSWORD = "Password needs an uppercase letter, a number and a special character"
    const val INVALID_CREDENTIALS = "No user with the given credentials"
}