package com.ecommerce.lock

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.data.redis.lock")
data class DistributedLockProperty(
    val keyPrefix: String,
    val retry: Int,
    val retryDelayMillis: Long,
    val lockTimeoutMillis: Long
) {
    init {
        val className = "DistributedLockProperty"

        require(keyPrefix.isNotEmpty()) { "$className: $NOT_EMPTY_PREFIX_MESSAGE" }
        require(retry >= 0) { "$className: $NOT_ALLOW_NEGATIVE_RETRY_MESSAGE" }
        require(retryDelayMillis >= 0) { "$className: $NOT_ALLOW_NEGATIVE_DELAY_MESSAGE" }
        require(lockTimeoutMillis > 0) { "$className: $INVALID_TIMEOUT_MESSAGE" }
    }

    companion object {
        const val NOT_EMPTY_PREFIX_MESSAGE = "Distributed lock prefix is required"
        const val NOT_ALLOW_NEGATIVE_RETRY_MESSAGE = "Distributed lock retry must be non-negative"
        const val NOT_ALLOW_NEGATIVE_DELAY_MESSAGE = "Distributed lock delay must be non-negative"
        const val INVALID_TIMEOUT_MESSAGE = "Distributed lock timeout must be higher than 0"
    }
}