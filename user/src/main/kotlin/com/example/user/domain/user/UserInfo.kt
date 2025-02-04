package com.example.user.domain.user

import java.time.LocalDateTime

data class UserInfo(
    val id: String,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

fun User.toInfo() = UserInfo(
    id = id,
    name = name,
    createdAt = audit.createdAt,
    updatedAt = audit.updatedAt
)