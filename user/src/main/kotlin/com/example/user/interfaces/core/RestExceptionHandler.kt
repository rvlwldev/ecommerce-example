package com.example.user.interfaces.core

import com.example.user.core.exception.BizException
import com.example.user.core.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(BizException::class)
    fun handleBizException(e: BizException): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(e.status.value(), e.message!!)
        return Mono.just(ResponseEntity.status(e.status).body(errorResponse))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error")
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse))
    }

}
