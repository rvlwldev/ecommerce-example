package com.ecommerce

import com.ecommerce.service.Member
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MemberUnitTest {

    @Test
    fun `success - create member with valid email format`() {
        assertDoesNotThrow {
            Member(name = "test", password = "pass", email = "test@test.com")
        }
    }

    @Test
    fun `success - update name successfully`() {
        val member = Member(name = "test", email = "test@test.com", password = "pass")
        assertDoesNotThrow {
            member.updateName("newName")
        }
    }

    @Test
    fun `success - update password successfully`() {
        val member = Member(name = "test", email = "test@test.com", password = "oldPass")
        assertDoesNotThrow {
            member.updatePassword("oldPass", "newPass")
        }
    }

    @Test
    fun `success - increase then decrease cash within balance`() {
        val member = Member(name = "test", email = "test@test.com", password = "pass")
        member.increaseCash(200)
        assertDoesNotThrow {
            member.decreaseCash(100)
        }
    }

    @Test
    fun `fail - create member with invalid email format`() {
        assertThrows<IllegalArgumentException> {
            Member(name = "test", password = "pass", email = "NotEmailFormat")
        }
    }

    @Test
    fun `fail - update name with empty string`() {
        val member = Member(name = "test", email = "test@test.com", password = "pass")
        assertThrows<IllegalArgumentException> {
            member.updateName("")
        }
    }

    @Test
    fun `fail - update password with incorrect current password`() {
        val member = Member(name = "test", email = "test@test.com", password = "oldPass")
        assertThrows<IllegalArgumentException> {
            member.updatePassword("wrongPass", "newPass")
        }
    }

    @Test
    fun `fail - decrease cash beyond current balance`() {
        val member = Member(name = "test", email = "test@test.com", password = "pass")
        assertThrows<IllegalStateException> {
            member.decreaseCash(100)
        }
    }

}