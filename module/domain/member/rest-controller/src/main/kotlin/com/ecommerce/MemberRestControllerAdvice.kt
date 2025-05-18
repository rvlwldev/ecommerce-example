package com.ecommerce

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MemberRestControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(e: Exception) = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body<CommonException>(CommonException())

    fun handleExpectedException(e: CommonException) = ResponseEntity
        .status(e.code.toInt())
        .body<CommonException>(e)

}