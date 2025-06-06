package com.ecommerce

import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

object RedisTestContainer {

    fun createContainer(port: Int = 6379) = GenericContainer(DockerImageName.parse("redis:latest")).apply {
        withExposedPorts(port)
        start()
    }

    fun <K, V> createTemplate(container: GenericContainer<*>, mappedPort: Int): RedisTemplate<K, V> {
        val config = RedisStandaloneConfiguration(container.host, mappedPort)
        val factory = LettuceConnectionFactory(config).apply { afterPropertiesSet() }

        return RedisTemplate<K, V>().apply {
            connectionFactory = factory
            afterPropertiesSet()
        }
    }

}
