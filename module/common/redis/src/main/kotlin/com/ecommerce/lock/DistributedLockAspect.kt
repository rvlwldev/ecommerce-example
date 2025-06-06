package com.ecommerce.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAspect(
    private val client: DistributedLockClient<String>,
    private val keyBuilder: LockKeyBuilder,
) {

    @Around("@annotation(distributedLock)")
    fun aroundDistributedLock(point: ProceedingJoinPoint, distributedLock: DistributedLock) = with(distributedLock) {
        val key = keyBuilder.extractKey(point, LockKey::class.java)
        val option = client.createOption(retry, delay, timeout)

        client.runWithLock(prefix, key, option) { point.proceed() }
    }

    @Around("@annotation(txDistributedLock)")
    fun aroundTransactionalDistributedLock(
        point: ProceedingJoinPoint, txDistributedLock: TransactionalDistributedLock
    ) = with(txDistributedLock) {
        val key = keyBuilder.extractKey(point, LockKey::class.java)
        val option = client.createOption(retry, delay, timeout)

        client.runWithTransactionalLock(prefix, key, option) { point.proceed() }
    }
}