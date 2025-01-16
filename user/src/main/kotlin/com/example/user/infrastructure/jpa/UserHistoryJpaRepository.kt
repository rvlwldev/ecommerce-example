package com.example.user.infrastructure.jpa

import com.example.user.domain.history.UserHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserHistoryJpaRepository : JpaRepository<UserHistory, Long> {
    fun findAllByIdOrderByCreatedAtDesc(id: String, pageable: Pageable): Page<UserHistory>
}