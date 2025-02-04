package com.example.user.domain.user

import com.example.user.domain.user.UserDto.HistoryInfo
import com.example.user.domain.user.UserDto.Info
import java.time.LocalDateTime

class UserDto {
    data class Info(
        val id: String,
        val name: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime?
    )

    data class HistoryInfo(
        val seq: Long,
        val newName: String,
        val oldName: String?,
        val createdAt: LocalDateTime
    )
}

fun User.toInfo() = Info(
    id = id,
    name = name,
    createdAt = audit.createdAt,
    updatedAt = audit.updatedAt
)

fun UserHistory.toInfo() = HistoryInfo(
    seq = seq,
    newName = newName,
    oldName = oldName,
    createdAt = createdAt
)