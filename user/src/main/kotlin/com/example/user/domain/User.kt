package com.example.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table(name = "users")
class User(
    @Id
    val id: Long? = null,
    private val username: String,
    private val password: String,
    val role: String = "USER",
    price: Long = 0L,
) : UserDetails {
    var price = price
        private set

    override fun getUsername() = username
    override fun getPassword() = password
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_$role"))

}