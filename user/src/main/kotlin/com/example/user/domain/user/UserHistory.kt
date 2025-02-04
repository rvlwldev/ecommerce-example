package com.example.user.domain.user

import com.example.user.domain.core.HistoryId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.time.LocalDateTime

enum class UserHistoryType {
    CREATE, CHANGE_NAME
}

@Entity
@IdClass(HistoryId::class)
class UserHistory(
    @Id
    val seq: Long = 0,

    @Id
    val id: String = "",

    @Column(columnDefinition = "VARCHAR(255)")
    val type: UserHistoryType = UserHistoryType.CREATE,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val newName: String = "",
    val oldName: String? = null,
)