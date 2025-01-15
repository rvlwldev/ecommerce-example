package com.example.user.domain.history

import com.example.user.domain.history.HistoryInfo.CashHistoryInfo
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.time.LocalDateTime


enum class CashHistoryType {
    USE, CHARGE
}

@Entity
@IdClass(HistoryId::class)
class CashHistory(
    @Id
    override val seq: Long = 0,

    @Id
    override val id: String = "",

    @Column(columnDefinition = "VARCHAR(255)")
    override val type: CashHistoryType,

    override val createdAt: LocalDateTime = LocalDateTime.now(),

    val amount: Long = 0
) : History {
    override fun toInfo() = CashHistoryInfo(this)
}