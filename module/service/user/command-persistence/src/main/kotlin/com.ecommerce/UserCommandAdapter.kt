package com.ecommerce

import com.ecommerce.lock.DistributedLockClient

class UserCommandAdapter(
    private val mapper: UserMapper,
    private val jpa: UserJpaRepository,
    private val locker: DistributedLockClient<String>
) : UserCommandPort {

    private val lockServiceKey = "user-persistence-lock"

    override fun findById(id: String): User? {
        val entity = jpa.findByIdOrNull(id)

        return if (entity != null) mapper.toDomain(entity)
        else null
    }

    override fun isExistEmail(email: String) =
        jpa.existsByEmail(email)

    override fun save(user: User) = locker.runWithLock(lockServiceKey, user.id.toString()) {
        val entity = mapper.toEntity(user)
        mapper.toDomain(jpa.save(entity))
    }

    // TODO : fix to logical delete
    override fun remove(user: User): Boolean {
        if (!jpa.existsById(user.id.toString()))
            return false

        jpa.deleteById(user.id.toString())
        return true
    }

}