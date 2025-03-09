package com.example.user.domain

import com.example.user.core.exception.BizException

enum class UserRole(val role: String) {
    USER("USER"),
    ADMIN("ADMIN");

    companion object {
        fun from(role: String) = UserRole.entries
            .find { it.name == role.uppercase() }
            ?: throw BizException(UserError.USER_ROLE_INVALID)
    }
}
