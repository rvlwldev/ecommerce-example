package com.example.user.domain.history

import com.example.user.domain.history.HistoryInfo.CashHistoryInfo
import com.example.user.domain.history.HistoryInfo.UserNameHistoryInfo
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class HistoryService(private val repo: HistoryRepository) {
    fun createUserNameHistory(
        id: String,
        type: UserHistoryType,
        newName: String,
        oldName: String?
    ): UserNameHistoryInfo {
        val history = UserHistory(id = id, type = type, newName = newName, oldName = oldName)

        return repo.save(history).toInfo()
    }

    fun createCashHistory(id: String, type: CashHistoryType, amount: Long): CashHistoryInfo {
        val history = CashHistory(id = id, type = type, amount = amount)

        return repo.save(history).toInfo()
    }

    fun findUserHistoryList(id: String, pageable: Pageable) =
        repo.findUserHistoryList(id, pageable).map { history -> history.toInfo() }

    fun findCashHistoryList(id: String, pageable: Pageable) =
        repo.findCashHistoryList(id, pageable).map { history -> history.toInfo() }
}