package com.example.user.domain.history

import java.time.LocalDateTime

class HistoryDto {
    data class CashLogInfo(
        val type: String,
        val amount: Long,
        val createdAt: LocalDateTime
    ) {
        constructor(history: CashHistory) : this(
            type = history.type.name,
            amount = history.amount,
            createdAt = history.audit.createdAt
        )
    }

    data class UserLogInfo(
        val type: String,
        val name: String,
        val createdAt: LocalDateTime
    ) {
        constructor(history: UserHistory) : this(
            type = history.type.name,
            name = history.name,
            createdAt = history.audit.createdAt
        )
    }
}