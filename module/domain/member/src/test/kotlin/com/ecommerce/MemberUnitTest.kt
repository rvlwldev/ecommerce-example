package com.ecommerce

import com.ecommerce.service.Member
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MemberUnitTest {

    class FakePasswordEncoder : PasswordEncoder {
        override fun encode(raw: String): String = "encoded:$raw"
        override fun matches(raw: String, encoded: String): Boolean = encoded == "encoded:$raw"
    }

    private val passwordEncoder = FakePasswordEncoder()

    @Test
    fun `success - create member with valid email format`() {
        assertDoesNotThrow {
            Member(name = "test", password = "encoded:pass", email = "test@test.com")
        }
    }

    @Test
    fun `success - update name successfully`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:pass")
        assertDoesNotThrow {
            member.updateName("newName")
        }
    }

    @Test
    fun `success - update password successfully`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:oldPass")
        assertDoesNotThrow {
            member.updatePassword("oldPass", "newPass", passwordEncoder)
        }
    }

    @Test
    fun `success - validate password with correct password`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:pass")
        assertDoesNotThrow {
            member.validatePassword("pass", passwordEncoder)
        }
    }

    @Test
    fun `success - increase then decrease cash within balance`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:pass")
        member.increaseCash(200)
        assertDoesNotThrow {
            member.decreaseCash(100)
        }
    }

    @Test
    fun `fail - create member with invalid email format`() {
        assertThrows<IllegalArgumentException> {
            Member(name = "test", password = "encoded:pass", email = "NotEmailFormat")
        }
    }

    @Test
    fun `fail - update name with empty string`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:pass")
        assertThrows<IllegalArgumentException> {
            member.updateName("")
        }
    }

    @Test
    fun `fail - update password with incorrect current password`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:oldPass")
        assertThrows<IllegalArgumentException> {
            member.updatePassword("wrongPass", "newPass", passwordEncoder)
        }
    }

    @Test
    fun `fail - validate password with incorrect password`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:pass")
        assertThrows<IllegalArgumentException> {
            member.validatePassword("wrong", passwordEncoder)
        }
    }

    @Test
    fun `fail - decrease cash beyond current balance`() {
        val member = Member(name = "test", email = "test@test.com", password = "encoded:pass")
        assertThrows<IllegalStateException> {
            member.decreaseCash(100)
        }
    }

}