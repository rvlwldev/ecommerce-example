package com.ecommerce

interface UserCommandPort {

    fun findById(id: String): User?
    fun isExistEmail(email: String): Boolean
    fun save(user: User): User
    fun remove(user: User): Boolean

}