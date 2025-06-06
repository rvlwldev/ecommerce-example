package com.ecommerce

import com.ecommerce.lock.LockKey
import com.ecommerce.lock.TransactionalDistributedLock
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

        if (!passwordEncoder.matches(password, member.password))
            throw MemberException.Unauthorized()

        return member
    }

    @TransactionalDistributedLock("user-cash")
    fun updateCash(@LockKey uuid: String, amount: Long): Member {
        val member = repo.find(UUID.fromString(uuid)) ?: throw MemberException.NotFound()

        member.updateCash(amount)

        return repo.save(member)
    }

    fun withdraw(uuid: UUID, password: String) {
        val member = repo.find(uuid) ?: throw MemberException.NotFound()

        if (!passwordEncoder.matches(password, member.password))
            throw MemberException.Unauthorized()

        repo.delete(member)
    }

}