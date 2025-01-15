package com.example.user.core.exception

import org.springframework.http.HttpStatus

class BizException(
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val code: String = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
    message: String = "서버 에러가 발생했습니다."
) : RuntimeException(message) {
    constructor(error: BizError) : this(error.status, error.code, error.message)
}