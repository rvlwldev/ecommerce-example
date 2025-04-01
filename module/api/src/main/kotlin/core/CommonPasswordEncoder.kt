package core

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CommonPasswordEncoder : PasswordEncoder {

    private val delegate = BCryptPasswordEncoder()

    override fun encode(raw: String) =
        delegate.encode(raw)

    override fun matches(raw: String, encoded: String) =
        delegate.matches(raw, encoded)

}