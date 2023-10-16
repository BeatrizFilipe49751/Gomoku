package daw.isel.pt.gomoku.services.exceptions



class NotFoundException(message: String) : Exception(message)
class InvalidCredentialsException(message: String) : Exception(message)
class UnauthorizedException(message: String) : Exception(message)

class AlreadyInLobbyException(message: String): Exception(message)
class NotYourTurnException(message: String): Exception(message)
class InvalidPlayException(message: String): Exception(message)
class GameAlreadyFinishedException(message: String): Exception(message)
