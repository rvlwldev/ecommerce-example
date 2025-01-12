package com.example.user.domain.user

import com.example.user.core.exception.BizException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repo: UserRepository) {
    fun find(uuid: Long) = repo.find(uuid) ?: throw BizException(UserError.NOT_FOUND)

    fun find(id: String) = repo.find(id) ?: throw BizException(UserError.NOT_FOUND)

    /**
     * @todo Lock needed
     * */
    @Transactional
    fun create(id: String, name: String): User {
        if (repo.find(id) != null) throw BizException(UserError.DUPLICATED_ID)

        val user = User(id, name)

        return repo.save(user)
    }

    fun chargeCash(id: String, amount: Long): User {
        val user = find(id)

        user.chargeCash(amount)

        return repo.save(user)
    }

    fun useCash(id: String, amount: Long): User {
        val user = find(id)

        user.useCash(amount)

        return repo.save(user)
    }
}