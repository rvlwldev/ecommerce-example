package com.example.user.core.exception

import org.springframework.http.HttpStatus

interface BizError {
    val status: HttpStatus
    val message: String
}
