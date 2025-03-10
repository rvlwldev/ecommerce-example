package com.example.user.domain

import com.example.user.core.exception.BizException
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
    val role: UserRole = UserRole.USER,
    address: String = "",
) : UserDetails {
    var address = address
        private set

    init {
        validateUsername(username)
    }

    constructor(username: String, password: String, role: String) : this(
        username = username,
        password = password,
        role = UserRole.from(role)
    )

    override fun getUsername() = username
    override fun getPassword() = password
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_$role"))

    companion object {
        private const val MIN_USERNAME_LENGTH = 8

        private fun validateUsername(username: String) {
            if (username.length < MIN_USERNAME_LENGTH)
                throw BizException(UserError.USER_NAME_TOO_SHORT)
        }
    }

}