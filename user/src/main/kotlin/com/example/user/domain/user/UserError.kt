package com.example.user.domain.user

import com.example.user.core.exception.BizError
import org.springframework.http.HttpStatus

enum class UserError(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : BizError {
    NOT_ENOUGH_CASH(HttpStatus.BAD_REQUEST, "USER_001", "캐시가 부족합니다."),
    INVALID_CHARGE_UNIT(HttpStatus.BAD_REQUEST, "USER_002", "캐시 충전 단위는 100 단위만 허용됩니다."),
    INVALID_NAME(HttpStatus.BAD_REQUEST, "USER_002", "이름에 숫자, 공백 및 특수문자는 포함할 수 없습니다.")
}