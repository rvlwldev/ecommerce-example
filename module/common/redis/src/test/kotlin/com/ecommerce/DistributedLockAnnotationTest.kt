package com.ecommerce

import com.ecommerce.lock.DistributedLockAspect
import com.ecommerce.lock.DistributedLockClient
import com.ecommerce.support.DummyService
import com.ecommerce.support.RedisTestConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertFailsWith

@ExtendWith(SpringExtension::class)
@EnableAspectJAutoProxy
@ContextConfiguration(classes = [RedisTestConfiguration::class, DistributedLockAspect::class, DummyService::class])
class DistributedLockAnnotationTest {

    @Autowired
    lateinit var service: DummyService

    @Autowired
    lateinit var txManager: PlatformTransactionManager

    @Autowired
    private lateinit var template: RedisTemplate<String, Boolean>

    @Autowired
    private lateinit var client: DistributedLockClient<String>

    @BeforeEach
    fun setUp() {
        template.connectionFactory?.connection?.serverCommands()?.flushAll()
        Mockito.reset(txManager)
    }

    @Test
    fun `should prevent concurrent execution`() {
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)
        val latch = CountDownLatch(10)

        val threads = List(100) {
            Thread {
                try {
                    service.doSomething("1", 2000L)
                    doneCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                } finally {
                    latch.countDown()
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        assertEquals(1, doneCount.get())
        assertEquals(99, failCount.get())
    }

    @Test
    fun `should prevent concurrent transactional execution`() {
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)
        val latch = CountDownLatch(10)

        val threads = List(100) {
            Thread {
                try {
                    service.doSomethingTransactional("1", 2000L)
                    doneCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                } finally {
                    latch.countDown()
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        assertEquals(1, doneCount.get())
        assertEquals(99, failCount.get())
    }

    @Test
    fun `should transactional lock call rollback on exception`() {
        assertFailsWith<RuntimeException> { service.doTransactionalFail("1") }
        verify(txManager).rollback(any())
    }

    @Test
    fun `should transactional multi-key lock call rollback on exception`() {
        assertFailsWith<RuntimeException> { service.doTransactionalFailWithMultiLockKey("1", "2") }
        verify(txManager).rollback(any())
    }

    @Test
    fun `should use different locks by combine @LockKey orders`() {
        val tasks = mutableListOf<Thread>()
        val count = AtomicInteger(0)
        tasks.add(Thread {
            runCatching { service.doSomethingKey("1", "2", "3") }
                .onFailure { count.incrementAndGet() }
        })
        tasks.add(Thread {
            runCatching { service.doSomethingKeyReverse("3", "2", "1") }
                .onFailure { count.incrementAndGet() }
        })
        tasks.add(Thread {
            runCatching { service.doSomethingKeyMixed("2", "3", "1") }
                .onFailure { count.incrementAndGet() }
        })

        client.acquire("order", "1:2:3", client.createOption(timeout = 10000L))
        tasks.forEach { it.start() }
        tasks.forEach { it.join() }

        assertEquals(3, count.get())
    }

    @Test
    fun `should wildcard key locks all execution`() {
        val tasks = mutableListOf<Thread>()
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        (1..101).forEach { index ->
            tasks.add(Thread {
                try {
                    service.doWildcardKey(index.toString())
                    doneCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            })
        }
        tasks.forEach { it.start() }
        tasks.forEach { it.join() }

        assertEquals(1, doneCount.get())
        assertEquals(100, failCount.get())
    }

    @Test
    fun `should wildcardOnNull locks all execution when parameter is null`() {
        val tasks = mutableListOf<Thread>()
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        (1..101).forEach {
            tasks.add(Thread {
                try {
                    service.doWildcardKeyOnNull(null)
                    doneCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            })
        }
        tasks.forEach { it.start() }
        tasks.forEach { it.join() }

        assertEquals(1, doneCount.get())
        assertEquals(100, failCount.get())
    }

    @Test
    fun `should not wildcardOnNull lock all execution when parameter is NOT null`() {
        val tasks = mutableListOf<Thread>()
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        (1..101).forEach { index ->
            tasks.add(Thread {
                try {
                    service.doWildcardKeyOnNull(index.toString())
                    doneCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            })
        }

        tasks.forEach { it.start() }
        tasks.forEach { it.join() }

        assertEquals(101, doneCount.get())
        assertEquals(0, failCount.get())
    }

}