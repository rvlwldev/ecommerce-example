package com.ecommerce

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.UUID

// TODO : Logger

@RestControllerAdvice
class DefaultRestControllerAdvice {

    @ExceptionHandler(CommonException::class)
    fun handleCommonException(e: CommonException) =
        ResponseEntity
            .status(e.status)
            .body(CommonErrorResponse(e.code, e.status, e.message ?: "Unexpected Internal Server Error Occurred"))

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(e: MethodArgumentTypeMismatchException) =
        if (e.requiredType == UUID::class.java)
            ResponseEntity
                .badRequest()
                .body(CommonErrorResponse("DEFAULT_400", 400, "Invalid Request"))
        else throw e

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) =
        ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.message)

}