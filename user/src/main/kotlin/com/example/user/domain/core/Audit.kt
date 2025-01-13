package com.example.user.domain.core

import jakarta.persistence.Embeddable
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@Embeddable
class Audit {
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    var updatedAt: LocalDateTime? = null
        private set

    var deletedAt: LocalDateTime? = null
        private set

    @PrePersist
    private fun onCreate() {
        createdAt = LocalDateTime.now()
    }

    @PreUpdate
    private fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }

    fun delete() {
        deletedAt = LocalDateTime.now()
    }
}