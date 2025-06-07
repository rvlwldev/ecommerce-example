package com.ecommerce

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepositoryImpl(private val jpa: UserJpaRepository) : UserRepository {

    override fun find(uuid: UUID) =
        jpa.findByIdOrNull(uuid)

    override fun find(email: String) =
        jpa.findByEmail(email)

    override fun save(member: User) =
        jpa.save(member)

    override fun delete(member: User) =
        jpa.delete(member)

}