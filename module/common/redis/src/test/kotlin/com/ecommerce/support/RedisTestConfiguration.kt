package com.ecommerce.support

import com.ecommerce.RedisTestContainer
import com.ecommerce.lock.DistributedLockClient
import com.ecommerce.lock.DistributedLockProperty
import com.ecommerce.lock.LockKeyBuilder
import org.mockito.Mockito.spy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.SimpleTransactionStatus
import org.testcontainers.containers.GenericContainer

@Configuration
@EnableAspectJAutoProxy
class RedisTestConfiguration {

    @Bean
    fun distributedLockProperty(): DistributedLockProperty {
        return DistributedLockProperty(
            keyPrefix = "test",
            retry = 1,
            retryDelayMillis = 100L,
            lockTimeoutMillis = 10000L
        )
    }

    @Bean
    fun redisContainer(): GenericContainer<*> =
        RedisTestContainer.createContainer(6379).apply { start() }

    @Bean
    fun redisConnectionFactory(container: GenericContainer<*>) =
        LettuceConnectionFactory(container.host, container.getMappedPort(6379))
            .apply { afterPropertiesSet() }

    @Bean
    fun redisTemplate(factory: LettuceConnectionFactory) =
        RedisTemplate<String, Boolean>().apply {
            connectionFactory = factory
            afterPropertiesSet()
        }

    @Bean
    fun lockKeyExtractor(property: DistributedLockProperty) = LockKeyBuilder(property)

    @Bean
    fun distributedLockClient(
        property: DistributedLockProperty,
        redisTemplate: RedisTemplate<String, Boolean>,
        extractor: LockKeyBuilder,
        txManager: PlatformTransactionManager
    ): DistributedLockClient<String> = DistributedLockClient(property, redisTemplate, extractor, txManager)

    @Bean
    fun spyTransactionManager() =
        spy(object : PlatformTransactionManager {
            override fun getTransaction(definition: TransactionDefinition?) = SimpleTransactionStatus()
            override fun commit(status: TransactionStatus) {}
            override fun rollback(status: TransactionStatus) {}
        })

}