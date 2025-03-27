package infrastructure

import org.springframework.stereotype.Repository
import service.MemberRepository

@Repository
class MemberRepositoryImpl(private val jpa: MemberJpaRepository) : MemberRepository {

}