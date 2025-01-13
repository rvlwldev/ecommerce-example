package com.example.user.domain.history

import com.example.user.domain.core.Audit
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class UserHistoryType {
    CREATE, CHANGE_NAME
}

@Entity
class UserHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val seq: Long = 0,

    val uuid: Long = 0,

    @Column(name = "type", columnDefinition = "VARCHAR(255)")
    val type: UserHistoryType,

    val name: String = "",

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "updatedAt", column = Column(insertable = false, updatable = false)),
        AttributeOverride(name = "deletedAt", column = Column(insertable = false, updatable = false))
    )
    val audit: Audit = Audit()
)

