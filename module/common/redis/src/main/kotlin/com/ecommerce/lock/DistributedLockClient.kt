package com.ecommerce.lock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.time.Duration

@Component
class DistributedLockClient<K>(
    private val property: DistributedLockProperty,
    private val redis: RedisTemplate<String, Boolean>,
    private val keyBuilder: LockKeyBuilder,
    private val txManager: PlatformTransactionManager
) {
    private val DEFAULT_OPTION = with(property) {
        createOption(retry, retryDelayMillis, lockTimeoutMillis)
    }

    data class Option(val retry: Int, val delay: Long, val timeout: Duration)

    fun createOption(
        retry: Int = property.retry,
        delay: Long = property.retryDelayMillis,
        timeout: Long = property.lockTimeoutMillis
    ): Option {
        val finalRetry = retry.takeIf { it >= 0 } ?: property.retry
        val finalDelay = delay.takeIf { it >= 0 } ?: property.retryDelayMillis
        val finalTimeout = timeout.takeIf { it > 0 } ?: property.lockTimeoutMillis

        return Option(finalRetry, finalDelay, Duration.ofMillis(finalTimeout))
    }

    fun acquire(prefix: String, key: K, option: Option = DEFAULT_OPTION): Boolean {
        require(prefix.isNotEmpty()) { NOT_ALLOW_EMPTY_PREFIX }

        with(option) {
            val key = keyBuilder.buildLockKey(prefix, key.toString())

            repeat(retry + 1) {
                val result = redis.opsForValue().setIfAbsent(key, true, timeout)
                if (result == true) {
                    release(prefix, key)
                    return true
                }

                Thread.sleep(delay)
            }
        }

        throw IllegalStateException(String.format(LOCK_ALREADY_HELD, key))
    }

    fun release(prefix: String, key: String) =
        redis.delete(keyBuilder.buildLockKey(prefix, key.toString()))

    fun isLocked(prefix: String, key: K) =
        redis.hasKey(keyBuilder.buildLockKey(prefix, key.toString()))

    fun <R> runWithLock(
        prefix: String,
        key: K,
        option: Option = DEFAULT_OPTION,
        action: () -> R
    ): R {
        try {
            acquire(prefix, key, option)
            return action()
        } catch (e: Exception) {
            throw e
        } finally {
            release(prefix, key.toString())
        }
    }

    fun <R> runWithTransactionalLock(
        prefix: String,
        key: K,
        option: Option = DEFAULT_OPTION,
        action: () -> R
    ): R {
        val status = txManager.getTransaction(DefaultTransactionDefinition())

        try {
            acquire(prefix, key, option)

            val result = action()
            txManager.commit(status)

            return result
        } catch (e: Exception) {
            txManager.rollback(status)
            throw e
        } finally {
            release(prefix, key.toString())
        }
    }

    companion object {
        const val NOT_ALLOW_EMPTY_PREFIX = "DistributedLockClient: Distributed lock prefix is required"
        const val LOCK_ALREADY_HELD = "DistributedLockClient: Lock already held for key: %s"
    }
}