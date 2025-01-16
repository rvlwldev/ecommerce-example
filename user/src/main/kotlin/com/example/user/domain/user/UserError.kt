package com.example.user.domain.user

import com.example.user.core.exception.BizError
import org.springframework.http.HttpStatus

enum class UserError(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : BizError {
    INVALID_NAME(HttpStatus.BAD_REQUEST, "USER_003", "이름에 숫자, 공백 및 특수문자는 포함할 수 없습니다."),
    UPDATE_SAME_NAME(HttpStatus.BAD_REQUEST, "USER_006", "같은 이름으로 다시 변경할 수 없습니다."),

    NOT_ALLOWED_ZERO(HttpStatus.BAD_REQUEST, "USER_002", "캐시 충전/사용은 음수, 0은 허용되지 않습니다."),
    INVALID_CHARGE_UNIT(HttpStatus.BAD_REQUEST, "USER_002", "캐시 충전 단위는 100 단위만 허용됩니다."),
    NOT_ENOUGH_CASH(HttpStatus.BAD_REQUEST, "USER_001", "캐시가 부족합니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "USER_004", "존재하지 않는 사용자입니다."),
    DUPLICATED_ID(HttpStatus.CONFLICT, "USER_005", "이미 존재하는 아이디입니다."),
}