package com.example.user.core.configuration

import io.lettuce.core.ReadFrom
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@ConfigurationProperties("spring.data.redis")
class RedisConfiguration {

    private val logger = LoggerFactory.getLogger(RedisConfiguration::class.java)

    var cluster: Cluster = Cluster()
    var lock: DistributedLock = DistributedLock()

    data class Cluster(
        var nodes: List<String> = listOf(),
        var timeout: Long = 0L
    )

    data class DistributedLock(
        var prefix: String = "",
        var timeout: Long = 0L,
        var retry: Int = 0,
        var delay: Long = 0
    )

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        logger.info("Redis Cluster Node List\n${cluster.nodes}")

        val clusterConfig = RedisClusterConfiguration(cluster.nodes)
        val clientConfig = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build()

        val factory = LettuceConnectionFactory(clusterConfig, clientConfig)
        factory.afterPropertiesSet()

        logger.info("Redis Cluster Connected")

        return factory
    }

    @Bean
    fun <K, V> redisTemplate(factory: LettuceConnectionFactory): RedisTemplate<K, V> {
        return RedisTemplate<K, V>().apply {
            connectionFactory = factory
        }
    }
}