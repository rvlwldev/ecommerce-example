package com.ecommerce

import com.ecommerce.configuration.DistributedLockProperty
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class DistributedLockClient<T>(
    private val redis: RedisTemplate<String, Boolean>,
    private val property: DistributedLockProperty
) {

    private fun validateLockSettings(timeout: Long, delay: Long, retry: Int) {
        require(timeout > 0) { String.format(INVALID_TIMEOUT_VALUE_MESSAGE, timeout) }
        require(delay >= 0) { String.format(INVALID_DELAY_VALUE_MESSAGE, delay) }
        require(retry >= 0) { String.format(INVALID_RETRY_VALUE_MESSAGE, retry) }
    }

    fun acquire(
        prefix: String,
        key: T,
        timeout: Long = property.timeout,
        delay: Long = property.delay,
        retry: Int = property.retry
    ): Boolean {
        validateLockSettings(timeout, delay, retry)

        val lockKey = "${property.prefix}:$prefix:${key.toString()}"
        val timeout = Duration.ofMillis(timeout)

        repeat(retry) {
            val success = redis.opsForValue().setIfAbsent(lockKey, true, timeout)
            if (success == true) return true

            Thread.sleep(delay)
        }

        throw IllegalStateException(String.format(LOCK_ALREADY_HELD_MESSAGE, key))
    }

    fun release(key: T, prefix: String = property.prefix) =
        redis.delete("$prefix:${key.toString()}")

    fun <R> runWithLock(
        prefix: String,
        key: T,
        action: () -> R,
        timeout: Long = property.timeout,
        delay: Long = property.delay,
        retry: Int = property.retry
    ) =
        try {
            acquire(prefix, key, timeout, delay, retry)
            action()
        } finally {
            release(key)
        }

    fun isLocked(key: T, prefix: String = property.prefix) =
        redis.hasKey("$prefix:${key.toString()}")

    companion object {
        const val LOCK_ALREADY_HELD_MESSAGE = "Failed to acquire lock. Lock already held for key: %s"
        const val INVALID_TIMEOUT_VALUE_MESSAGE = "Timeout must be positive : %d"
        const val INVALID_DELAY_VALUE_MESSAGE = "Timeout must be non-negative : %d"
        const val INVALID_RETRY_VALUE_MESSAGE = "Timeout must be non-negative : %d"
    }

}