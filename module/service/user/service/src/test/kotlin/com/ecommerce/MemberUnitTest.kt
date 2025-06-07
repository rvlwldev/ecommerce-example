package com.ecommerce

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MemberUnitTest {

    @Test
    fun `success - create member with valid email format`() {
        assertDoesNotThrow {
            User(name = "test", password = "pass", email = "test@test.com")
        }
    }

    @Test
    fun `success - update name successfully`() {
        val member = User(name = "test", email = "test@test.com", password = "pass")
        assertDoesNotThrow {
            member.updateName("newName")
        }
    }

    @Test
    fun `success - update password successfully`() {
        val member = User(name = "test", email = "test@test.com", password = "oldPass")
        assertDoesNotThrow {
            member.updatePassword("oldPass", "newPass")
        }
    }

    @Test
    fun `success - increase then decrease cash within balance`() {
        val member = User(name = "test", email = "test@test.com", password = "pass")
        member.updateCash(200)
        assertDoesNotThrow {
            member.updateCash(-100)
        }
    }

    @Test
    fun `fail - create member with invalid email format`() {
        assertThrows<Exception> {
            User(name = "test", password = "pass", email = "NotEmailFormat")
        }
    }

    @Test
    fun `fail - update name with empty string`() {
        val member = User(name = "test", email = "test@test.com", password = "pass")
        assertThrows<CommonException> {
            member.updateName("")
        }
    }

    @Test
    fun `fail - update password with incorrect current password`() {
        val member = User(name = "test", email = "test@test.com", password = "oldPass")
        assertThrows<CommonException> {
            member.updatePassword("wrongPass", "newPass")
        }
    }

    @Test
    fun `fail - decrease cash beyond current balance`() {
        val member = User(name = "test", email = "test@test.com", password = "pass")
        assertThrows<CommonException> {
            member.updateCash(-100)
        }
    }

}