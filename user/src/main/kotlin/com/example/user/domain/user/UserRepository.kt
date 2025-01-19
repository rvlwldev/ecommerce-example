package com.example.user.domain.user

interface UserRepository {
    fun find(id: String): User?
    fun save(user: User): User
}