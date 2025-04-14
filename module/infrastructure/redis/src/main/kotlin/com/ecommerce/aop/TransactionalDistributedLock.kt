package com.ecommerce.aop

import com.ecommerce.DistributedLockClient
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionalDistributedLock(
    val keys: Array<String>
)

@Aspect
@Component
class TransactionalDistributedLockAspect(
    private val client: DistributedLockClient,
    private val tx: PlatformTransactionManager
) {

    @Around("@annotation(com.ecommerce.aop.TransactionalDistributedLock)")
    fun transactionalLock(joinPoint: ProceedingJoinPoint, distributedLock: TransactionalDistributedLock): Any? {
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

        val status = tx.getTransaction(DefaultTransactionDefinition())

        try {
            val result = joinPoint.proceed()
            tx.commit(status)
            return result
        } catch (ex: Exception) {
            tx.rollback(status)
            throw ex
        } finally {
            client.releaseLock(key)
        }
    }

}