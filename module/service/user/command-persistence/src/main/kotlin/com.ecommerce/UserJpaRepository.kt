package com.ecommerce

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserCommandEntity, String> {

    fun findByIdOrNull(id: String): UserCommandEntity?
    fun existsByEmail(email: String): Boolean

}