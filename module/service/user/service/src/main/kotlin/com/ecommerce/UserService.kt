package com.ecommerce

import com.ecommerce.lock.LockKey
import com.ecommerce.lock.TransactionalDistributedLock
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val repo: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun create(name: String, email: String, password: String) =
        repo.save(User(name, email, passwordEncoder.encode(password)))

    fun find(uuid: UUID) =
        repo.find(uuid) ?: throw UserException.NotFound()

    fun find(email: String, password: String): User {
        val user = repo.find(email) ?: throw UserException.Unauthorized()

        if (!passwordEncoder.matches(password, user.password))
            throw UserException.Unauthorized()

        return user
    }

    @TransactionalDistributedLock("user-cash")
    fun updateCash(@LockKey uuid: String, amount: Long): User {
        val user = repo.find(UUID.fromString(uuid)) ?: throw UserException.NotFound()

        user.updateCash(amount)

        return repo.save(user)
    }

    fun withdraw(uuid: UUID, password: String) {
        val user = repo.find(uuid) ?: throw UserException.NotFound()

        if (!passwordEncoder.matches(password, user.password))
            throw UserException.Unauthorized()

        repo.delete(user)
    }

}