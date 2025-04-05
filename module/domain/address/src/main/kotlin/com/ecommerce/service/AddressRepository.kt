package com.ecommerce.service

import java.util.UUID

interface AddressRepository {
    fun find(uuid: UUID): Address?
    fun findAll(memberId: UUID): MutableList<Address>
    fun save(address: Address): Address?
    fun save(addresses: List<Address>): List<Address>
    fun delete(address: Address)
}