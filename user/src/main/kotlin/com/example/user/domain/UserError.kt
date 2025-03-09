package com.example.user.domain

import com.example.user.core.exception.BizError
import org.springframework.http.HttpStatus

enum class UserError(
    override val status: HttpStatus,
    override val message: String
) : BizError {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found"),
    USER_NAME_CONFLICT(HttpStatus.UNAUTHORIZED, "Already Exists User"),
    USER_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid Password")

}
