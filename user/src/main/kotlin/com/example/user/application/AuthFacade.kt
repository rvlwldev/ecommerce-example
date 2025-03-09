package com.example.user.application

import com.example.user.domain.UserService
import com.example.user.interfaces.auth.AuthResponse
import org.springframework.stereotype.Service

@Service
class AuthFacade(private val service: UserService) {

    fun signUp(username: String, password: String) =
        service.signUp(username, password)
            .flatMap { user -> service.signIn(username, password) }
            .map { token -> AuthResponse.Token(token) }

    fun signIn(username: String, password: String) =
        service.signIn(username, password)
            .map { token -> AuthResponse.Token(token) }

}