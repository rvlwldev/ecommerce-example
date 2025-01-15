package com.example.user.domain.history

import java.time.LocalDateTime


interface History {
    val seq: Long
    val id: String
    val type: Enum<*>
    val createdAt: LocalDateTime

    fun toInfo(): HistoryInfo
}