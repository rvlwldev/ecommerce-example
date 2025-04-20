package com.ecommerce

import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MemberRepositoryImpl(private val jpa: MemberJpaRepository) : MemberRepository {

    override fun find(uuid: UUID) =
        jpa.findById(uuid).orElse(null)

    override fun find(email: String) =
        jpa.findByEmail(email).orElse(null)

    override fun save(member: Member) =
        jpa.save(member)

    override fun delete(member: Member) =
        jpa.delete(member)

}