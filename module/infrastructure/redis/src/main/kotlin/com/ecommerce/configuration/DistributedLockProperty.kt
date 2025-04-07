package com.ecommerce.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.data.redis")
data class DistributedLockProperty(
    var prefix: String,
    var timeout: Long,
    var retry: Int,
    var delay: Long
)