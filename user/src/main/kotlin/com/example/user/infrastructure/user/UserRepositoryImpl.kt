package com.example.user.infrastructure.user

import com.example.user.domain.User
import com.example.user.domain.UserRepository
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class UserRepositoryImpl(private val r2: UserR2) : UserRepository {

    override fun findByUsername(username: String) =
        r2.findByUsername(username).switchIfEmpty(Mono.empty())

    override fun save(user: User) =
        r2.save<User>(user)

}

interface UserR2 : R2dbcRepository<User, Long> {

    fun findByUsername(username: String): Mono<User>

}