package com.example.user.core.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http.csrf { csrf -> csrf.disable() }
            // TODO : design architecture
            .authorizeExchange { auth ->
                auth.pathMatchers("/auth/**").permitAll()

                auth.anyExchange().authenticated()
            }
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}