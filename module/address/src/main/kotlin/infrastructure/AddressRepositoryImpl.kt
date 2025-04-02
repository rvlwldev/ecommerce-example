package infrastructure

import service.Address
import service.AddressRepository
import java.util.UUID

class AddressRepositoryImpl(private val jpa: AddressJpaRepository) : AddressRepository {

    override fun find(uuid: UUID) =
        jpa.findById(uuid).orElse(null)

    override fun findAll(memberId: UUID) =
        jpa.findAllByMemberId(memberId)

    override fun save(address: Address) =
        jpa.save(address)

    override fun save(addresses: List<Address>) =
        jpa.saveAll(addresses)

    override fun delete(address: Address) =
        jpa.delete(address)

}