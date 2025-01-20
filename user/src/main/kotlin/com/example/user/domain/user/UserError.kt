package com.example.user.domain.user

import com.example.user.core.exception.BizError
import org.springframework.http.HttpStatus

enum class UserError(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : BizError {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_USER_001", "존재하지 않는 사용자입니다."),
    USER_DUPLICATED_ID(HttpStatus.CONFLICT, "ERR_USER_002", "이미 존재하는 아이디입니다."),
    USER_INVALID_ID(HttpStatus.BAD_REQUEST, "ERR_USER_003", "올바르지 않은 아이디입니다."),

    NAME_INVALID(HttpStatus.BAD_REQUEST, "ERR_USERNAME_001", "이름에 숫자, 공백 및 특수문자는 포함할 수 없습니다."),
    NAME_DUPLICATED_UPDATE(HttpStatus.BAD_REQUEST, "ERR_USERNAME_002", "같은 이름으로 다시 변경할 수 없습니다."),

    CASH_NOT_ALLOWED_ZERO(HttpStatus.BAD_REQUEST, "ERR_CASH_001", "캐시 충전/사용은 음수, 0은 허용되지 않습니다."),
    CASH_INVALID_CHARGE_UNIT(HttpStatus.BAD_REQUEST, "ERR_CASH_002", "캐시 충전 단위는 100 단위만 허용됩니다."),
    CASH_NOT_ENOUGH_CASH(HttpStatus.BAD_REQUEST, "ERR_CASH_003", "캐시가 부족합니다."),
}