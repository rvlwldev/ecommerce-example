package service

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberService(private val repo: MemberRepository) {

    fun create(name: String, email: String, password: String) =
        repo.save(Member(name, email, password))

    fun find(uuid: UUID) =
        repo.find(uuid) ?: throw IllegalArgumentException()

    fun chargeCash(uuid: UUID, amount: Long): Member {
        val member = repo.find(uuid) ?: throw IllegalArgumentException()

        member.increaseCash(amount)

        return member
    }

    fun useCash(uuid: UUID, amount: Long): Member {
        val member = repo.find(uuid) ?: throw IllegalArgumentException()

        member.decreaseCash(amount)

        return member
    }

    fun withdraw(uuid: UUID, password: String) {
        val member = repo.find(uuid) ?: throw IllegalArgumentException()

        repo.delete(member)
    }

}