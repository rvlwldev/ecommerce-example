package com.ecommerce.configuration

import io.lettuce.core.ReadFrom
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.connection.RedisClusterConfiguration as RedisClusterConfigurator

@Configuration
class RedisClusterConfiguration(private val property: RedisClusterProperty) {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val clusterConfig = RedisClusterConfigurator(property.nodes)
        val clientConfig = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build()

        return LettuceConnectionFactory(clusterConfig, clientConfig)
    }

    @Bean
    fun <K, V> redisClient(factory: LettuceConnectionFactory) =
        RedisTemplate<K, V>().apply { connectionFactory = factory }

}