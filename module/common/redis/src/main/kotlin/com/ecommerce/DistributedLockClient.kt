package com.ecommerce

import com.ecommerce.configuration.DistributedLockProperty
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class DistributedLockClient(
    private val redis: RedisTemplate<String, Boolean>,
    private val property: DistributedLockProperty
) {

    fun acquireLock(key: String): Boolean {
        val lockKey = "${property.prefix}:$key"
        val timeout = Duration.ofMillis(property.timeout)

        repeat(property.retry) {
            val success = redis.opsForValue().setIfAbsent(lockKey, true, timeout)
            if (success == true) return true

            Thread.sleep(property.delay)
        }

        return false
    }

    fun releaseLock(key: String) =
        redis.delete(key)

}