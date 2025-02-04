package com.example.user.infrastructure

import com.example.user.domain.core.HistoryId
import com.example.user.domain.user.User
import com.example.user.domain.user.UserHistory
import com.example.user.domain.user.UserHistoryType
import com.example.user.domain.user.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpa: UserJpaRepository,
    private val historyJpa: UserHistoryJpaRepository
) : UserRepository {

    override fun find(id: String) = jpa.findById(id).orElse(null)

    override fun create(user: User): User {
        val history = UserHistory(type = UserHistoryType.CREATE, newName = user.name)
        historyJpa.save(history)

        return jpa.save(user)
    }

    override fun updateName(user: User, oldName: String): User {
        val history = UserHistory(type = UserHistoryType.CHANGE_NAME, newName = user.name, oldName = oldName)
        historyJpa.save(history)

        return jpa.save(user)
    }

}

interface UserJpaRepository : JpaRepository<User, String>

interface UserHistoryJpaRepository : JpaRepository<UserHistory, HistoryId> {

    fun findAllByIdOrderByCreatedAtDesc(id: String, pageable: Pageable): Page<UserHistory>

}