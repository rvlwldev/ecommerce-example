package com.ecommerce.infrastructure

import com.ecommerce.service.Address
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AddressJpaRepository : JpaRepository<Address, UUID> {

    fun findAllByMemberId(memberId: UUID): MutableList<Address>

}