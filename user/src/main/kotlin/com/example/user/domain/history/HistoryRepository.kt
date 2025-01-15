package com.example.user.domain.history

import org.springframework.data.domain.Pageable

interface HistoryRepository {
    fun <T : History> save(history: T): T
    fun <T : History> find(id: String, pageable: Pageable): List<T>

    fun findUserHistoryList(id: String, pageable: Pageable): List<UserHistory>
    fun findCashHistoryList(id: String, pageable: Pageable): List<CashHistory>
}