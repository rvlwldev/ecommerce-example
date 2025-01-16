package com.example.user.domain.core

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.PrePersist
import java.time.LocalDateTime

@Embeddable
class Audit {
    @Column(updatable = false)
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

    fun update() {
        updatedAt = LocalDateTime.now()
    }

    fun delete() {
        deletedAt = LocalDateTime.now()
    }
}