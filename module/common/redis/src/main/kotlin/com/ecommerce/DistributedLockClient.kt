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

    fun acquire(
        key: T,
        prefix: String = property.prefix,
        timeout: Long = property.timeout,
        delay: Long = property.delay,
        retry: Int = property.retry
    ): Boolean {
        val lockKey = "$prefix:${key.toString()}"
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

    fun <R> runWithLock(key: T, action: () -> R) =
        try {
            acquire(key)
            action()
        } finally {
            release(key)
        }

    fun isLocked(key: T, prefix: String = property.prefix) =
        redis.hasKey("$prefix:${key.toString()}")

    companion object {
        const val LOCK_ALREADY_HELD_MESSAGE = "Failed to acquire lock. Lock already held for key: %s"
    }

}