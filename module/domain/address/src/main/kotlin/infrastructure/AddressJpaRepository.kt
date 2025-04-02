package infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import service.Address
import java.util.UUID

interface AddressJpaRepository : JpaRepository<Address, UUID> {

    fun findAllByMemberId(memberId: UUID): MutableList<Address>

}