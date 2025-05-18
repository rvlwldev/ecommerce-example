package com.ecommerce

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberJpaRepository : JpaRepository<Member, UUID> {

    fun findByEmail(email: String): Member?

}