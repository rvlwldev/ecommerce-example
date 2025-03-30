package core.configuration

import core.CommonPasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Security {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return CommonPasswordEncoder()
    }

}