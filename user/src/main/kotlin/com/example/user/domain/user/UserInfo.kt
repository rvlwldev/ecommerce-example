package com.example.user.domain.user

import java.time.LocalDateTime

data class UserInfo(
    val id: String,
    val name: String,
    val cash: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
) {
    constructor(user: User) : this(
        id = user.id,
        name = user.name,
        cash = user.cash,
        createdAt = user.audit.createdAt,
        updatedAt = user.audit.updatedAt
    )
}
