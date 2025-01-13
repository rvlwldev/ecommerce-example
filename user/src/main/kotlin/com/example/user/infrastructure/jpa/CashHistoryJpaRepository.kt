package com.example.user.infrastructure.jpa

import com.example.user.domain.history.CashHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CashHistoryJpaRepository : JpaRepository<CashHistory, Long> {
    fun findAllByUuid(uuid: Long, pageable: Pageable): Page<CashHistory>
}