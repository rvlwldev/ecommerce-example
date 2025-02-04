package com.example.user.domain.point

import com.example.user.domain.point.PointDto.HistoryInfo
import com.example.user.domain.point.PointDto.Info
import java.time.LocalDateTime

class PointDto {
    data class Info(
        val id: Long,
        val userId: String,
        val amount: Long,
        val lastChargedAt: LocalDateTime?,
        val lastUsedAt: LocalDateTime?
    )

    data class HistoryInfo(
        val seq: Long,
        val type: String,
        val amount: Long,
        val createdAt: LocalDateTime
    )
}


fun Point.toInfo() = Info(
    id = id,
    userId = user.name,
    amount = amount,
    lastChargedAt = chargeAudit.updatedAt,
    lastUsedAt = useAudit.updatedAt
)

fun PointHistory.toInfo() = HistoryInfo(
    seq = seq,
    type = type.name,
    amount = amount,
    createdAt = createdAt
)