package com.example.user.core.exception

import org.springframework.http.HttpStatus

class BizException(status: HttpStatus, code: String, message: String) : RuntimeException(message) {
    constructor(error: BizError) : this(error.status, error.code, error.message)
}