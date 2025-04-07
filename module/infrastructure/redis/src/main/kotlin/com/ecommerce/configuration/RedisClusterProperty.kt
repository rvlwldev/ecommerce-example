package com.ecommerce.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.data.redis")
data class RedisClusterProperty(
    val nodes: List<String> = listOf(),
    val timeout: Long = 0L
)