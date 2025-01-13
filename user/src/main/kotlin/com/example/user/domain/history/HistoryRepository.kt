package com.example.user.domain.history

import org.springframework.data.domain.Pageable

interface HistoryRepository {
    fun save(history: UserHistory): UserHistory
    fun save(history: CashHistory): CashHistory
    fun findUserLogList(uuid: Long, pageable: Pageable): List<UserHistory>
    fun findCashLogList(uuid: Long, pageable: Pageable): List<CashHistory>
}