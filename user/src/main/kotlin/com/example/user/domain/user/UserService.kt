package com.example.user.domain.user

import com.example.user.core.exception.BizException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repo: UserRepository) {
    fun find(id: String): UserInfo {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        return user.toInfo()
    }

    @Transactional
    fun create(id: String, name: String): UserInfo {
        if (repo.find(id) != null) throw BizException(UserError.USER_DUPLICATED_ID)

        val user = repo.save(User(id, name))

        return user.toInfo()
    }

    @Transactional
    fun updateName(id: String, newName: String): UserInfo {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        // TODO : to Facade build a logic that save history too
        val oldName = user.name
        user.changeName(newName)

        return repo.save(user).toInfo()
    }

}