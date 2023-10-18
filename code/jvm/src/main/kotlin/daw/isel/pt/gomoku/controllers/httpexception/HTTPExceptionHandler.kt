package daw.isel.pt.gomoku.controllers.httpexception

import daw.isel.pt.gomoku.controllers.models.ErrorResponse
import daw.isel.pt.gomoku.services.exceptions.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.SQLException

@ControllerAdvice
class HTTPExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [NotFoundException::class] )
    fun exceptionHandlerNotFound(e: NotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(),  e.message ?: "No message provided")
        log.info("Handling NotFoundException: ${e.message}")
        return ResponseEntity
            .status(errorResponse.status)
            .body(errorResponse)
    }
    @ExceptionHandler(value = [AlreadyInLobbyException::class, IllegalArgumentException::class, InvalidCredentialsException::class, GameError::class] )
    fun exceptionHandlerBadRequest(e: Exception): ResponseEntity<ErrorResponse>{
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(),  e.message ?: "No message provided")
        log.info("Handling ${e::class.java.simpleName}: ${e.message}")
        return ResponseEntity
            .status(errorResponse.status)
            .body(errorResponse)
    }


    @ExceptionHandler(value = [UnauthorizedException::class] )
    fun exceptionHandlerUnauthorized(e: UnauthorizedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.message ?: "No message provided")
        log.info("Handling UnauthorizedException ${e.message}")
        return ResponseEntity
            .status(errorResponse.status)
            .body(errorResponse)
    }

    @ExceptionHandler(value = [SQLException::class])
    fun handleSQLException(e: SQLException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "DATABASE ERROR")
        log.info("Handling SQLException ${e.message}")
        return ResponseEntity
            .status(errorResponse.status)
            .body(errorResponse)
    }

    companion object {
        private val log = LoggerFactory.getLogger(HTTPExceptionHandler::class.java)
    }

}