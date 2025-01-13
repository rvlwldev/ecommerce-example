package com.example.user.domain.history

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class HistoryService(private val repo: HistoryRepository) {
    fun saveUserLogs(uuid: Long, type: UserHistoryType, name: String) =
        UserHistory(uuid = uuid, type = type, name = name)
            .let { history -> repo.save(history) }
            .let { history -> HistoryDto.UserLogInfo(history) }

    fun saveCashLogs(uuid: Long, type: CashHistoryType, amount: Long) =
        CashHistory(uuid = uuid, type = type, amount = amount)
            .let { history -> repo.save(history) }
            .let { history -> HistoryDto.CashLogInfo(history) }

    fun getUserLogs(uuid: Long, pageable: Pageable) =
        repo.findUserLogList(uuid, pageable).map { log -> HistoryDto.UserLogInfo(log) }

    fun getCashLogs(uuid: Long, pageable: Pageable) =
        repo.findCashLogList(uuid, pageable).map { log -> HistoryDto.CashLogInfo(log) }
}