package com.ecommerce

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@TestConfiguration
class RedisTestConfiguration {

    @Bean
    @Primary
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val config = RedisStandaloneConfiguration(
            RedisTestContainer.host,
            RedisTestContainer.port
        )
        return LettuceConnectionFactory(config)
    }

    @Bean
    @Primary
    fun <K, V> redisClient(factory: LettuceConnectionFactory) =
        RedisTemplate<K, V>().apply { connectionFactory = factory }

}