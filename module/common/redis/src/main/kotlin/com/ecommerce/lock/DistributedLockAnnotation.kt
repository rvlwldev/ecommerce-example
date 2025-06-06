package com.ecommerce.lock

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LockKey(
    val order: Long = 0,
    val wildcard: Boolean = false,
    val wildcardOnNull: Boolean = false,
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val prefix: String,
    val retry: Int = -1,
    val delay: Long = -1,
    val timeout: Long = -1

)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionalDistributedLock(
    val prefix: String,
    val retry: Int = -1,
    val delay: Long = -1,
    val timeout: Long = -1
)