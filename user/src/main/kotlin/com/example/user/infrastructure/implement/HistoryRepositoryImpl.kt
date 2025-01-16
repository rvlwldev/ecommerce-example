package com.example.user.infrastructure.implement

import com.example.user.core.exception.BizException
import com.example.user.domain.history.CashHistory
import com.example.user.domain.history.History
import com.example.user.domain.history.HistoryRepository
import com.example.user.domain.history.UserHistory
import com.example.user.infrastructure.jpa.CashHistoryJpaRepository
import com.example.user.infrastructure.jpa.UserHistoryJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


@Repository
class HistoryRepositoryImpl(
    private val userJpa: UserHistoryJpaRepository,
    private val cashJpa: CashHistoryJpaRepository
) : HistoryRepository {

    override fun <T : History> save(history: T) = when (history) {
        is UserHistory -> userJpa.save(history) as T
        is CashHistory -> cashJpa.save(history) as T
        else -> throw BizException()
    }

    override fun findUserHistoryList(id: String, pageable: Pageable) =
        userJpa.findAllByIdOrderByCreatedAtDesc(id, pageable).content

    override fun findCashHistoryList(id: String, pageable: Pageable) =
        cashJpa.findAllByIdOrderByCreatedAtDesc(id, pageable).content
}