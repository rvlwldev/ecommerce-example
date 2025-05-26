package com.ecommerce.aop

import com.ecommerce.DistributedLockClient
import com.ecommerce.support.KeyExtractor
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val keys: Array<String>
)

@Aspect
@Component
class DistributedLockAspect(private val client: DistributedLockClient) {

    @Around("@annotation(com.ecommerce.aop.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint, annotation: DistributedLock): Any? {
        val key = KeyExtractor.extract(joinPoint, annotation.keys)
        if (!client.acquireLock(key)) throw IllegalStateException("Lock failed for key: $key")

        return try {
            joinPoint.proceed()
        } finally {
            client.releaseLock(key)
        }
    }

}