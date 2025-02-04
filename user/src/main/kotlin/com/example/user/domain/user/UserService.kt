package com.example.user.domain.user

import com.example.user.core.exception.BizException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repo: UserRepository) {
    fun find(id: String): UserDto.Info {
        val user = repo.find(id) ?: throw BizException(UserError.USER_NOT_FOUND)

        return user.toInfo()
    }

    @Transactional
    fun create(id: String, name: String): UserDto.Info {
        if (repo.find(id) != null) throw BizException(UserError.USER_DUPLICATED_ID)

        val user = repo.create(User(id, name))

        return user.toInfo()
    }

    @Transactional
    fun updateName(userId: String, newName: String): UserDto.Info {
        val user = repo.find(userId) ?: throw BizException(UserError.USER_NOT_FOUND)
        val oldName = user.name

        user.changeName(newName)

        return repo.updateName(user, oldName).toInfo()
    }

}