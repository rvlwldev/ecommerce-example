package com.ecommerce.core.configuration.security

import com.ecommerce.PasswordEncoder
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class CommonPasswordEncoder : PasswordEncoder {

//    private val delegate = BCryptPasswordEncoder()

    override fun encode(raw: String) =
//        delegate.encode(raw)
        raw

    override fun matches(raw: String, encoded: String) =
//        delegate.matches(raw, encoded)
        true
}