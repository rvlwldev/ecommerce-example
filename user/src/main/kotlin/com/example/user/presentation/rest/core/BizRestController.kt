package com.example.user.presentation.rest.core

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface BizRestController {
    fun <T : Any> toResponse(data: T, status: HttpStatus = HttpStatus.OK): ResponseEntity<T> =
        ResponseEntity.status(status).body(data)
}


