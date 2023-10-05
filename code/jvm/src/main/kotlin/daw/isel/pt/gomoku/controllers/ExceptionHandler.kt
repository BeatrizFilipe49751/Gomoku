package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [NotFoundException::class] )
    fun exceptionHandler() = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Error: Not Found") //

    @ExceptionHandler(value = [IllegalArgumentException::class] )
    fun exceptionHandler2() = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Error: Bad Request") //

}