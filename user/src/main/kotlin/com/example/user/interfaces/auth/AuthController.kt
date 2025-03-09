package com.example.user.interfaces.auth

import com.example.user.application.AuthFacade
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController(private val facade: AuthFacade) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: AuthRequest.Register): Mono<AuthResponse.Token> =
        facade.signUp(request.username, request.password)

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request: AuthRequest.Register): Mono<AuthResponse.Token> =
        facade.signIn(request.username, request.password)

}