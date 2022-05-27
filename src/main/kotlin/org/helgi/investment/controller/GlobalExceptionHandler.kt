package org.helgi.investment.controller

import org.helgi.investment.NotFoundException
import org.helgi.investment.ValidationException
import org.helgi.investment.model.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ApiError> {
        logger.error("Resource not found", ex)
        return handleError(ex, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ApiError> {
        logger.error("Validation error occurred", ex)
        return handleError(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiError> {
        logger.error("Unexpected error occurred", ex)
        return handleError(ex, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    fun handleError(ex: Throwable, status: HttpStatus): ResponseEntity<ApiError> {
        return handleError(status.reasonPhrase, ex.message, status)
    }

    fun handleError(message: String, debugMessage: String?, status: HttpStatus): ResponseEntity<ApiError> {
        return ResponseEntity(ApiError(message, debugMessage), status)
    }
}