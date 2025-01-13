package com.example.user.infrastructure.implement

import com.example.user.domain.history.CashHistory
import com.example.user.domain.history.HistoryRepository
import com.example.user.domain.history.UserHistory
import com.example.user.infrastructure.jpa.CashHistoryJpaRepository
import com.example.user.infrastructure.jpa.UserHistoryJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


@Repository
class HistoryRepositoryImpl(
    private val cashJpa: CashHistoryJpaRepository,
    private val userJpa: UserHistoryJpaRepository
) : HistoryRepository {
    override fun save(history: UserHistory) =
        userJpa.save(history)

    override fun save(history: CashHistory) =
        cashJpa.save(history)

    override fun findUserLogList(uuid: Long, pageable: Pageable): List<UserHistory> =
        userJpa.findAllByUuid(uuid, pageable).content

    override fun findCashLogList(uuid: Long, pageable: Pageable): List<CashHistory> =
        cashJpa.findAllByUuid(uuid, pageable).content
}