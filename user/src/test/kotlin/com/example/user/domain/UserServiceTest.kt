package com.example.user.domain

import com.example.user.core.exception.BizException
import com.example.user.domain.history.CashHistoryType
import com.example.user.domain.history.HistoryService
import com.example.user.domain.history.UserHistoryType
import com.example.user.domain.user.User
import com.example.user.domain.user.UserRepository
import com.example.user.domain.user.UserService
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.Test
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    lateinit var repo: UserRepository

    @Mock
    lateinit var history: HistoryService

    @InjectMocks
    lateinit var sut: UserService

    @Test
    fun `find user by id - success`() {
        val id = "testId"
        val name = "testName"
        val user = User(id, name)

        `when`(repo.find(id)).thenReturn(user)

        val result = sut.find(id)
        assertEquals(id, result.id)
        assertEquals(name, result.name)
        verify(repo, times(1)).find(id)
    }

    @Test
    fun `find user by id - fail`() {
        `when`(repo.find(any<String>())).thenReturn(null)
        assertThrows<BizException> { sut.find("any") }
    }

    @Test
    fun `create user - success`() {
        val id = "testId"
        val name = "testName"
        val user = User(id, name)

        `when`(repo.save(any<User>())).thenReturn(user)

        val result = sut.create(id, name)
        assertEquals(id, result.id)
        assertEquals(name, result.name)
        verify(repo, times(1)).save(any<User>())
        verify(history, times(1)).createUserNameHistory(id, UserHistoryType.CREATE, name, null)
    }

    @Test
    fun `create user - fail`() {
        val user = User("testId", "testName")

        `when`(repo.find(any<String>())).thenReturn(user)
        assertThrows<BizException> { sut.create("testId", "testName") }
        verify(repo, never()).save(any<User>())
        verify(history, never()).createUserNameHistory("testId", UserHistoryType.CREATE, "testName", null)
    }

    @Test
    fun `update user's name - success`() {
        val id = "testId"
        val name = "testName"
        val newName = "newName"
        val user = User(id, name)

        `when`(repo.find(id)).thenReturn(user)
        `when`(repo.save(user)).thenReturn(user)

        val result = sut.updateName(id, newName)
        assertEquals(newName, result.name)
        verify(repo, times(1)).save(any<User>())
        verify(history, times(1)).createUserNameHistory(id, UserHistoryType.CHANGE_NAME, newName, name)
    }

    @Test
    fun `update user's name - fail`() {
        val id = "testId"
        val name = "testName"
        val newName = "testName"
        val user = User(id, name)

        `when`(repo.find(id)).thenReturn(user)
        assertThrows<BizException> { sut.updateName(id, newName) }
        verify(repo, never()).save(user)
        verify(history, never()).createUserNameHistory(id, UserHistoryType.CHANGE_NAME, newName, name)
    }

    @Test
    fun `charge user's cash - success`() {
        val id = "testId"
        val name = "testName"
        val user = User(id, name)
        val amount = 100L

        `when`(repo.find(id)).thenReturn(user)
        `when`(repo.save(user)).thenReturn(user)

        val result = sut.chargeCash(id, amount)
        assertEquals(amount, result.cash)
        verify(repo, times(1)).save(user)
        verify(history, times(1)).createCashHistory(id, CashHistoryType.CHARGE, amount)
    }

    @Test
    fun `charge user's cash - fail`() {
        val id = "testId"
        val name = "testName"
        val user = User(id, name)
        val fails = listOf<Long>(0, 1, -1, -100, -101, 99, 101)

        `when`(repo.find(id)).thenReturn(user)

        fails.forEach { fail ->
            assertThrows<BizException> { sut.chargeCash(id, fail) }
            verify(history, never()).createCashHistory(id, CashHistoryType.CHARGE, fail)
        }
        verify(repo, never()).save(user)
        verify(repo, times(fails.count())).find(id)
    }

    @Test
    fun `use user's cash - success`() {
        val id = "testId"
        val name = "testName"
        val user = User(id, name)
        val save = 1000L
        val use = 100L

        `when`(repo.find(id)).thenReturn(user)
        `when`(repo.save(user)).thenReturn(user)
        sut.chargeCash(id, save)

        val result = sut.useCash(id, use)
        assertEquals(save - use, result.cash)
    }

    @Test
    fun `use user's cash - fail`() {
        val id = "testId"
        val name = "testName"
        val user = User(id, name)
        val fails = listOf<Long>(0, -1, -100, -1000)

        `when`(repo.find(id)).thenReturn(user)

        fails.forEach { fail ->
            assertThrows<BizException> { sut.useCash(id, fail) }
            verify(history, never()).createCashHistory(id, CashHistoryType.USE, fail)
        }
        verify(repo, never()).save(user)
        verify(repo, times(fails.count())).find(id)
    }
}