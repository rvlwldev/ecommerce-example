package com.example.user.domain.user

import com.example.user.core.exception.BizException
import com.example.user.domain.history.CashHistoryType
import com.example.user.domain.history.HistoryService
import com.example.user.domain.history.UserHistoryType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val repo: UserRepository,
    private val historyService: HistoryService
) {
    fun find(id: String): UserInfo {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        return user.toInfo()
    }

    @Transactional
    fun create(id: String, name: String): UserInfo {
        if (repo.find(id) != null) throw BizException(UserError.USER_DUPLICATED_ID)

        val user = repo.save(User(id, name))

        historyService.createUserNameHistory(user.id, UserHistoryType.CREATE, user.name, null)
        return user.toInfo()
    }

    @Transactional
    fun updateName(id: String, newName: String): UserInfo {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        val oldName = user.name
        user.changeName(newName)

        historyService.createUserNameHistory(user.id, UserHistoryType.CHANGE_NAME, user.name, oldName)
        return repo.save(user).toInfo()
    }

    @Transactional
    fun chargeCash(id: String, amount: Long): UserInfo {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        user.chargeCash(amount)

        historyService.createCashHistory(user.id, CashHistoryType.CHARGE, amount)
        return repo.save(user).toInfo()
    }

    @Transactional
    fun useCash(id: String, amount: Long): UserInfo {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        user.useCash(amount)

        historyService.createCashHistory(user.id, CashHistoryType.USE, amount)
        return repo.save(user).toInfo()
    }
}