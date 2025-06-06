package com.ecommerce

import com.ecommerce.lock.DistributedLockClient
import com.ecommerce.lock.DistributedLockProperty
import com.ecommerce.lock.LockKeyBuilder
import com.ecommerce.support.RedisTestConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.PlatformTransactionManager
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [RedisTestConfiguration::class])
class DistributedLockClientTest {

    @Autowired
    private lateinit var sut: DistributedLockClient<String>

    @Autowired
    private lateinit var keyBuilder: LockKeyBuilder

    @Autowired
    private lateinit var template: RedisTemplate<String, Boolean>

    @Autowired
    private lateinit var txManager: PlatformTransactionManager

    @BeforeEach
    fun setUp() {
        template.connectionFactory?.connection?.serverCommands()?.flushAll()
    }

    @Test
    fun `should acquire only one by one`() {
        val prefix = "test"
        val key = "key"

        assertTrue { sut.acquire(prefix, key) }
        assertThrows<IllegalStateException> { sut.acquire(prefix, key) }
        assertTrue { sut.release(prefix, key) }
        assertTrue { sut.acquire(prefix, key) }
    }

    @Test
    fun `should not affect other keys`() {
        val prefix = "test"

        assertTrue { sut.acquire(prefix, "1") }
        assertTrue { sut.acquire(prefix, "2") }
        assertTrue { sut.acquire(prefix, "3") }
    }

    @Test
    fun `should throw when acquiring already held lock`() {
        val prefix = "test"
        val key = "key"
        val message = "Lock already held for key"

        sut.acquire(prefix, key)

        val exception = assertThrows<IllegalStateException> { sut.acquire(prefix, key) }
        assertTrue(exception.message!!.contains(message))
    }

    @Test
    fun `should allow releasing non-existent lock without error`() {
        assertDoesNotThrow { sut.release("test", "non-existent-key") }
    }

    @Test
    fun `should release lock even if exception occurs`() {
        val prefix = "test"

        assertThrows<RuntimeException> {
            sut.runWithLock(prefix, "key") { throw RuntimeException("error") }
        }
        assertTrue(sut.acquire(prefix, "key"))
    }

    @Test
    fun `'acquire' must ensure only one execution for the same key`() {
        val prefix = "test"
        val key = "conflict"
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        val tasks = List(10) {
            Thread {
                runCatching {
                    sut.acquire(prefix, key, sut.createOption(retry = 0))
                    Thread.sleep(100)
                    sut.release(prefix, key)
                    doneCount.incrementAndGet()
                }.onFailure { failCount.incrementAndGet() }
            }
        }
        tasks.forEach { it.start() }
        tasks.forEach { it.join() }

        assertEquals(1, doneCount.get())
        assertEquals(9, failCount.get())

    }

    @Test
    fun `runWithLock should prevent concurrent execution for the same key`() {
        val key = "conflict"
        val doneCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        val tasks = List(100) {
            Thread {
                try {
                    sut.runWithLock<Int>("test", key, sut.createOption(retry = 0)) {
                        Thread.sleep(200)
                        doneCount.incrementAndGet()
                    }
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            }
        }
        tasks.forEach { it.start() }
        tasks.forEach { it.join() }

        assertEquals(1, doneCount.get())
        assertEquals(99, failCount.get())
    }

    @Test
    fun `should retry the exact number of times`() {
        val retry = 100
        val delay = 10L
        val timeout = 2000L

        val redisPrefix = "d-lock"
        val servicePrefix = "retry"
        val serviceKey = "test"

        val spyOperation = spy(template.opsForValue())
        val spyTemplate = spy(template)
        val property = DistributedLockProperty(redisPrefix, retry, delay, timeout)
        val keyBuilder = LockKeyBuilder(property)
        val client = DistributedLockClient<String>(property, spyTemplate, keyBuilder, txManager)

        val lockKey = keyBuilder.buildLockKey(servicePrefix, serviceKey)

        `when`(spyTemplate.opsForValue()).thenReturn(spyOperation)

        client.acquire(servicePrefix, serviceKey) // + 1

        assertThrows<IllegalStateException> { client.acquire(servicePrefix, serviceKey) } // + 1
        verify(spyOperation, times(retry + 2)).setIfAbsent(eq(lockKey), eq(true), any())
    }

    @Test
    fun `timeout must release lock and stop task`() {
        val shortTimeoutMs = 1000L
        val retry = 3
        val delay = 200L

        val shortProperty = DistributedLockProperty(
            keyPrefix = "d-lock-timeout-test",
            retry = retry,
            retryDelayMillis = delay,
            lockTimeoutMillis = shortTimeoutMs
        )
        val client = DistributedLockClient<String>(shortProperty, template, keyBuilder, txManager)

        val prefix = "timeout"
        val key = "key-timeout"

        client.acquire(prefix, key)

        val start = System.currentTimeMillis()
        val result = runCatching {
            Thread.sleep(shortTimeoutMs + 100)
            client.acquire(prefix, key)
        }
        val elapsed = System.currentTimeMillis() - start
        assertTrue(result.isSuccess)
        assertTrue(elapsed >= shortTimeoutMs)
    }
}