package com.ecommerce.support

import com.ecommerce.lock.DistributedLock
import com.ecommerce.lock.LockKey
import com.ecommerce.lock.TransactionalDistributedLock
import org.springframework.stereotype.Component

@Component
class DummyService {

    @DistributedLock("lock")
    fun doSomething(@LockKey param: String, sleep: Long = 1000L) =
        Thread.sleep(sleep)

    @TransactionalDistributedLock("lock")
    fun doSomethingTransactional(@LockKey param: String, sleep: Long = 1000L) =
        Thread.sleep(sleep)

    @TransactionalDistributedLock("tx")
    fun doTransactionalFail(@LockKey param: String): String {
        throw RuntimeException()
    }

    @TransactionalDistributedLock("tx")
    fun doTransactionalFailWithMultiLockKey(@LockKey(1) p1: String, @LockKey(2) p2: String) {
        throw RuntimeException()
    }

    @DistributedLock("order")
    fun doSomethingKey(
        @LockKey(order = 1) p1: String,
        @LockKey(order = 2) p2: String,
        @LockKey(order = 3) p3: String,
        sleep: Long = 3000L
    ) = Thread.sleep(sleep)

    @DistributedLock("order")
    fun doSomethingKeyReverse(
        @LockKey(order = 3) p1: String,
        @LockKey(order = 2) p2: String,
        @LockKey(order = 1) p3: String,
        sleep: Long = 3000L
    ) = Thread.sleep(sleep)

    @DistributedLock("order")
    fun doSomethingKeyMixed(
        @LockKey(order = 2) p1: String,
        @LockKey(order = 3) p2: String,
        @LockKey(order = 1) p3: String,
        sleep: Long = 3000L
    ) = Thread.sleep(sleep)

    @DistributedLock("wildcard")
    fun doWildcardKey(@LockKey(wildcard = true) p1: String, sleep: Long = 1000L) =
        Thread.sleep(sleep)

    @DistributedLock("wildcardOnNull")
    fun doWildcardKeyOnNull(@LockKey(wildcardOnNull = true) p1: String?, sleep: Long = 1000L) =
        Thread.sleep(sleep)

}