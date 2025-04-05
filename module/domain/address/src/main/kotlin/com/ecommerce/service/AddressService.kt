package com.ecommerce.service

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AddressService(private val repo: AddressRepository) {

    fun find(uuid: UUID) =
        repo.find(uuid) ?: throw IllegalArgumentException()

    fun findAll(memberId: UUID) =
        repo.findAll(memberId)

    fun save(memberId: UUID, create: AddressCommand.Create): Address {
        val address = Address(
            memberId = memberId,
            country = create.country,
            city = create.city,
            street = create.street,
            detail = create.detail
        )

        return repo.save(address) ?: throw IllegalArgumentException()
    }

    fun delete(uuid: UUID) {
        val address = find(uuid)

        repo.delete(address)
    }

}