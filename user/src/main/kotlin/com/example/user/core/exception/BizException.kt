package com.example.user.core.exception

import org.springframework.http.HttpStatus

class BizException(
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message: String = "Server Error Occurred"
) : RuntimeException(message) {

    constructor(error: BizError) : this(
        error.status, error.message
    )

}