package com.ecommerce

import com.ecommerce.aop.TransactionalDistributedLock
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberService(
    private val repo: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun create(name: String, email: String, password: String) =
        repo.save(Member(name, email, passwordEncoder.encode(password)))

    fun find(uuid: UUID) =
        repo.find(uuid) ?: throw MemberException.NotFound()

    fun find(email: String, password: String): Member {
        val member = repo.find(email) ?: throw MemberException.Unauthorized()

        if (passwordEncoder.matches(password, member.password))
            throw MemberException.Unauthorized()

        return member
    }

    @TransactionalDistributedLock(keys = ["uuid"])
    fun chargeCash(uuid: UUID, amount: Long): Member {
        val member = repo.find(uuid) ?: throw throw MemberException.NotFound()

        member.increaseCash(amount)

        return repo.save(member)
    }

    @TransactionalDistributedLock(keys = ["uuid"])
    fun useCash(uuid: UUID, amount: Long): Member {
        val member = repo.find(uuid) ?: throw throw MemberException.NotFound()

        member.decreaseCash(amount)

        return repo.save(member)
    }

    fun withdraw(uuid: UUID, password: String) {
        val member = repo.find(uuid) ?: throw MemberException.NotFound()

        repo.delete(member)
    }

}