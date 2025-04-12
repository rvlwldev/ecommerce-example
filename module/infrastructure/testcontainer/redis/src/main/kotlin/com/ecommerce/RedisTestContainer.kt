package com.ecommerce

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

object RedisTestContainer {
    private val container = GenericContainer<Nothing>(DockerImageName.parse("redis:latest")).apply {
        withExposedPorts(6379)
        start()
    }

    val host: String get() = container.host
    val port: Int get() = container.firstMappedPort
    val uri: String get() = "redis://$host:$port"
}