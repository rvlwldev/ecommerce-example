package com.example.user.domain.point

import com.example.user.domain.core.HistoryId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.time.LocalDateTime

enum class PointHistoryType {
    USE, CHARGE
}

@Entity
@IdClass(HistoryId::class)
class PointHistory(
    @Id
    val seq: Long = 0,

    @Id
    val id: String = "",

    @Column(columnDefinition = "VARCHAR(255)")
    val type: PointHistoryType = PointHistoryType.USE,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val amount: Long = 0,
) {
    constructor(userId: String, type: PointHistoryType, point: Point) : this(
        id = userId,
        type = type,
        amount = point.amount
    )
}