package com.example.user.domain.history

import java.time.LocalDateTime

open class HistoryInfo {
    data class UserNameHistoryInfo(
        val seq: Long,
        val newName: String,
        val oldName: String?,
        val createdAt: LocalDateTime
    ) : HistoryInfo() {
        constructor(history: UserHistory) : this(
            seq = history.seq,
            newName = history.newName,
            oldName = history.oldName,
            createdAt = history.createdAt
        )
    }

    data class CashHistoryInfo(
        val seq: Long,
        val type: String,
        val amount: Long,
        val createdAt: LocalDateTime
    ) : HistoryInfo() {
        constructor(history: CashHistory) : this(
            seq = history.seq,
            type = history.type.name,
            amount = history.amount,
            createdAt = history.createdAt
        )
    }
}