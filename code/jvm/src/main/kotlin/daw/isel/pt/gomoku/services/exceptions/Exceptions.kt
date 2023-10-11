package daw.isel.pt.gomoku.services.exceptions



class NotFoundException(message: String) : Exception(message)
class InvalidCredentialsException(message: String) : Exception(message)
class UnauthorizedException(message: String) : Exception(message)
