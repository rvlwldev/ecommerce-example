package com.ecommerce

import com.ecommerce.service.Member
import com.ecommerce.service.MemberRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MemberInfraTest {

    class FakePasswordEncoder : PasswordEncoder {
        override fun encode(raw: String): String = "encoded:$raw"
        override fun matches(raw: String, encoded: String): Boolean = encoded == "encoded:$raw"
    }

    private val repo = mock(MemberRepository::class.java)
    private val service = MemberService(repo, FakePasswordEncoder())

    @Test
    fun `fail - not allowed charge cash concurrently`() {
        // given
        val member = repo.save(Member("test", "test@email.com", "test"))
        val latch = CountDownLatch(1)
        val executor = Executors.newFixedThreadPool(2)

        // when
        executor.submit {
            var chargedMember = service.chargeCash(member.id, 100)
            latch.countDown()
            Thread.sleep(3000)
        }

        // then
        executor.submit {
            latch.await()
            assertThrows<Exception> { service.chargeCash(member.id, 200) }
        }

        executor.shutdown()
        executor.awaitTermination(10, TimeUnit.SECONDS)
    }

    @Test
    fun `fail - not allowed use cash concurrently`() {
        // given
        val member = repo.save(Member("test", "test@email.com", "test"))
        val latch = CountDownLatch(1)
        val executor = Executors.newFixedThreadPool(2)

        // when
        executor.submit {
            service.useCash(member.id, 100)
            latch.countDown()
            Thread.sleep(3000)
        }

        // then
        executor.submit {
            latch.await()
            assertThrows<Exception> { service.chargeCash(member.id, 200) }
        }

        executor.shutdown()
        executor.awaitTermination(10, TimeUnit.SECONDS)
    }
}