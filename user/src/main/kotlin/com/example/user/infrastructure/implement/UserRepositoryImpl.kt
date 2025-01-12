package com.example.user.infrastructure.implement

import com.example.user.domain.user.User
import com.example.user.domain.user.UserRepository
import com.example.user.infrastructure.jpa.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val jpa: UserJpaRepository) : UserRepository {
    override fun find(uuid: Long) = jpa.findById(uuid).orElse(null)

    override fun find(id: String) = jpa.findById(id).orElse(null)

    override fun save(user: User) = jpa.save(user)
}