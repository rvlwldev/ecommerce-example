package com.example.user.domain

import com.example.user.core.JWT
import com.example.user.core.exception.BizException
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val repo: UserRepository,
    private val jwt: JWT,
    private val encoder: PasswordEncoder
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String) =
        repo.findByUsername(username)
            .map { user -> user as UserDetails }
            .switchIfEmpty(Mono.error(BizException(UserError.USER_NOT_FOUND)))

    fun signUp(username: String, password: String) =
        repo.findByUsername(username)
            .flatMap<User> { Mono.error(BizException(UserError.USER_NAME_CONFLICT)) }
            .switchIfEmpty(Mono.defer {
                val hashedPassword = encoder.encode(password)
                val user = User(username = username, password = hashedPassword)
                return@defer repo.save(user)
            })

    fun signIn(username: String, password: String): Mono<String> =
        repo.findByUsername(username)
            .switchIfEmpty(Mono.error(BizException(UserError.USER_NOT_FOUND)))
            .flatMap { user ->
                if (!encoder.matches(password, user.password))
                    return@flatMap Mono.error(BizException(UserError.USER_INVALID_CREDENTIALS))

                Mono.just(jwt.generateToken(user.username))
            }

}