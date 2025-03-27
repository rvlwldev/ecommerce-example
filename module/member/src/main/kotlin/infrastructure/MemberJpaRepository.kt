package infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import service.Member
import java.util.UUID

interface MemberJpaRepository : JpaRepository<UUID, Member>