package com.example.user.infrastructure.jpa

import com.example.user.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findById(id: String): Optional<User>
}