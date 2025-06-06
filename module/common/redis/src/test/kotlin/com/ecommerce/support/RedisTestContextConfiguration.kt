package com.ecommerce.support

import com.ecommerce.lock.DistributedLockClient
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [RedisTestConfiguration::class, DistributedLockClient::class])
open class RedisTestContextConfiguration