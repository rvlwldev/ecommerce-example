package com.example.user.domain.history

import com.example.user.domain.core.Audit
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id

enum class CashHistoryType {
    USE, CHARGE
}

@Entity
class CashHistory(
    @Id
    val seq: Long = 0,

    val uuid: Long = 0,

    @Column(name = "type", columnDefinition = "VARCHAR(255)")
    val type: CashHistoryType,

    val amount: Long = 0,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "updatedAt", column = Column(insertable = false, updatable = false)),
        AttributeOverride(name = "deletedAt", column = Column(insertable = false, updatable = false))
    )
    val audit: Audit = Audit()
)
