package com.example.user.domain

import reactor.core.publisher.Mono

interface UserRepository {
    fun findByUsername(username: String): Mono<User>
    fun save(user: User): Mono<User>
}