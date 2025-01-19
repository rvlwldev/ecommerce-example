package com.example.user.infrastructure.jpa

import com.example.user.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, String>