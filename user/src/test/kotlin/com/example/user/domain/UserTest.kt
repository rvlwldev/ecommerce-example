package com.example.user.domain

import com.example.user.core.exception.BizException
import com.example.user.domain.user.User
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test


class UserTest {

    @Test
    fun `can't change name to blank name or invalid character`() {
        val user = User("TestId", "TestName")

        assertThrows<BizException> { user.changeName("") }
        assertThrows<BizException> { user.changeName("!") }
        assertThrows<BizException> { user.changeName("new name") }
        assertThrows<BizException> { user.changeName("TestName") }
        assertDoesNotThrow { user.changeName("newName") }
    }

    @Test
    fun `cash must be charged in increments of hundred`() {
        val user = User("TestId", "TestName")

        assertDoesNotThrow { user.chargeCash(1000) }
        assertDoesNotThrow { user.chargeCash(12312312312300) }

        assertThrows<BizException> { user.chargeCash(1) }
        assertThrows<BizException> { user.chargeCash(123123) }
    }

    @Test
    fun `when user use or charge cash it must not be under zero`() {
        val user = User("TestId", "TestName")

        user.chargeCash(1000)

        assertThrows<BizException> { user.useCash(2000) }
    }

}