package com.ecommerce.infrastructure

import com.ecommerce.service.Member
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import java.util.Optional
import java.util.UUID

interface MemberJpaRepository : JpaRepository<Member, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @EntityGraph(attributePaths = ["addresses"])
    override fun findById(uuid: UUID): Optional<Member>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    fun findByEmailAndPassword(email: String, password: String): Optional<Member>

}