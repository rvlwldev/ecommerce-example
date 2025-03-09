package com.example.user.core.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.security.jwt")
class JwtProperty {
    lateinit var secret: String
    var expiration: Long = 0L
}