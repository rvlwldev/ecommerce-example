package com.example.user.core

import com.example.user.core.configuration.JwtProperty
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWT(private val property: JwtProperty) {

    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(property.secret))
    private val expiration = property.expiration

    private fun getClaims(token: String) =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

    fun getUsername(token: String) = getClaims(token).subject

    fun generateToken(username: String) =
        Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()

    fun validateToken(token: String) =
        try {
            getClaims(token).expiration.after(Date())
        } catch (e: Exception) {
            false
        }

}
