package com.ecommerce.aop

import com.ecommerce.DistributedLockClient
import com.ecommerce.support.KeyExtractor
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
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
    private val txManager: PlatformTransactionManager
) {

    @Around("@annotation(com.ecommerce.aop.TransactionalDistributedLock)")
    fun aroundTransactionalLock(joinPoint: ProceedingJoinPoint, annotation: TransactionalDistributedLock): Any? {
        val key = KeyExtractor.extract(joinPoint, annotation.keys)
        if (!client.acquireLock(key)) throw IllegalStateException("Lock failed for key: $key")

        val status = txManager.getTransaction(DefaultTransactionDefinition())
        try {
            val result = joinPoint.proceed()
            txManager.commit(status)
            return result
        } catch (ex: Exception) {
            txManager.rollback(status)
            throw ex
        } finally {
            client.releaseLock(key)
        }
    }

}