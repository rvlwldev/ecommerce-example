package com.ecommerce.infrastructure

import com.ecommerce.service.Member
import com.ecommerce.service.MemberRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MemberRepositoryImpl(private val jpa: MemberJpaRepository) : MemberRepository {

    override fun find(uuid: UUID) =
        jpa.findById(uuid).orElse(null)

    override fun find(email: String, encryptedPassword: String) =
        jpa.findByEmailAndPassword(email, encryptedPassword).orElse(null)

    override fun save(member: Member) =
        jpa.save(member)

    override fun delete(member: Member) =
        jpa.delete(member)

}