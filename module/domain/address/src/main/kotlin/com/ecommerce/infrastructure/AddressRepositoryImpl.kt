package com.ecommerce.infrastructure

import java.util.UUID

class AddressRepositoryImpl(private val jpa: AddressJpaRepository) : com.ecommerce.service.AddressRepository {

    override fun find(uuid: UUID) =
        jpa.findById(uuid).orElse(null)

    override fun findAll(memberId: UUID) =
        jpa.findAllByMemberId(memberId)

    override fun save(address: com.ecommerce.service.Address) =
        jpa.save(address)

    override fun save(addresses: List<com.ecommerce.service.Address>) =
        jpa.saveAll(addresses)

    override fun delete(address: com.ecommerce.service.Address) =
        jpa.delete(address)

}