package com.ecommerce.aop

import com.ecommerce.DistributedLockClient
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
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
    fun lock(joinPoint: ProceedingJoinPoint, distributedLock: DistributedLock): Any? {
        val method = (joinPoint.signature as MethodSignature).method
        val args = joinPoint.args
        val parameterNames = method.parameters.map { it.name }
        val parts = mutableListOf<String>()

        distributedLock.keys.forEach { targetName ->
            val index = parameterNames.indexOf(targetName)
            if (index == -1) throw IllegalArgumentException("Key name '$targetName' not found in parameters")

            parts.add(args[index].toString())
        }

        val key = parts.joinToString(":")
        if (!client.acquireLock(key)) throw IllegalStateException("Lock failed for key: $key")

        try {
            return joinPoint.proceed()
        } finally {
            client.releaseLock(key)
        }
    }

}