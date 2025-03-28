package infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import service.Member
import java.util.Optional
import java.util.UUID

interface MemberJpaRepository : JpaRepository<Member, UUID> {
    fun findByEmailAndPassword(email: String, password: String): Optional<Member>
}