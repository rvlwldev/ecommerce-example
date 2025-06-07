package com.ecommerce

import java.util.UUID

interface UserRepository {

    fun find(uuid: UUID): User?
    fun find(email: String): User?
    fun save(member: User): User
    fun delete(member: User)

}