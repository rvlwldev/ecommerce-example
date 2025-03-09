package com.example.user.interfaces.auth

class AuthRequest {

    data class Register(
        val username: String,
        val password: String
    )

}