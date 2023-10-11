package daw.isel.pt.gomoku.controllers.httpexception

import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import daw.isel.pt.gomoku.services.exceptions.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.SQLException

@ControllerAdvice
class HTTPExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [NotFoundException::class] )
    fun exceptionHandlerNotFound(e: NotFoundException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Error: NOT FOUND")

    @ExceptionHandler(value = [IllegalArgumentException::class, InvalidCredentialsException::class, SQLException::class] )
    fun exceptionHandlerBadRequest(e: Exception) = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Error: Bad Request") //


    @ExceptionHandler(value = [UnauthorizedException::class] )
    fun exceptionHandlerUnauthorized(e: UnauthorizedException) = ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Error: Unauthorized access") //

}