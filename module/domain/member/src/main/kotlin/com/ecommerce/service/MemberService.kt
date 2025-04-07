package com.ecommerce.service

import com.ecommerce.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MemberService(
    private val repo: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun create(name: String, email: String, password: String) =
        repo.save(Member(name, email, passwordEncoder.encode(password)))

    fun find(uuid: UUID) =
        repo.find(uuid) ?: throw IllegalArgumentException()

    fun find(email: String, password: String) {
        val member = repo.find(email) ?: throw IllegalArgumentException()

        member.validatePassword(password, passwordEncoder)
    }

    @Transactional
    fun chargeCash(uuid: UUID, amount: Long): Member {
        val member = repo.find(uuid) ?: throw IllegalArgumentException()

        member.increaseCash(amount)

        return repo.save(member)
    }

    @Transactional
    fun useCash(uuid: UUID, amount: Long): Member {
        val member = repo.find(uuid) ?: throw IllegalArgumentException()

        member.decreaseCash(amount)

        return repo.save(member)
    }

    fun withdraw(uuid: UUID, password: String) {
        val member = repo.find(uuid) ?: throw IllegalArgumentException()

        repo.delete(member)
    }

}