package com.example.user.domain.history

import com.example.user.domain.history.HistoryInfo.UserNameHistoryInfo
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
    override val seq: Long = 0,

    @Id
    override val id: String = "",

    @Column(columnDefinition = "VARCHAR(255)")
    override val type: UserHistoryType = UserHistoryType.CREATE,

    override val createdAt: LocalDateTime = LocalDateTime.now(),

    val newName: String = "",
    val oldName: String? = null,
) : History {
    override fun toInfo() = UserNameHistoryInfo(this)
}