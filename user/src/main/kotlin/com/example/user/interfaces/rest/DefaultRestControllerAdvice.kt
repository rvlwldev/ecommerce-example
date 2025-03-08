package com.example.user.interfaces.rest

import com.example.user.core.exception.BizException
import com.example.user.core.exception.ErrorResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class DefaultRestControllerAdvice : ResponseEntityExceptionHandler() {
    private final val logger: Logger = LoggerFactory.getLogger(DefaultRestControllerAdvice::class.java)

    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(exception: Exception) = BizException()
        .let { e -> ResponseEntity(ErrorResponse(e.message!!), e.status) }
        .also { logger.error("Unexpected error occurred: $exception.message", exception.stackTrace) }

    @ExceptionHandler(BizException::class)
    fun handleBizException(e: BizException) =
        ResponseEntity.status(e.status).body(ErrorResponse(e.message!!))
}


