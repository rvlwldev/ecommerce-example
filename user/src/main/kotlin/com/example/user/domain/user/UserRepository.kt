package com.example.user.domain.user

interface UserRepository {
    fun find(id: String): User?
    fun create(user: User): User
    fun updateName(user: User, oldName: String): User
}