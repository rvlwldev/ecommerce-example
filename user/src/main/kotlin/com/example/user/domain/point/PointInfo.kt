package com.example.user.domain.point

import java.time.LocalDateTime

data class PointInfo(
    val id: Long,
    val userId: String,
    val amount: Long,
    val lastChargedAt: LocalDateTime?,
    val lastUsedAt: LocalDateTime?
)

fun Point.toInfo() = PointInfo(
    id = id,
    userId = user.name,
    amount = amount,
    lastChargedAt = chargeAudit.updatedAt,
    lastUsedAt = useAudit.updatedAt
)