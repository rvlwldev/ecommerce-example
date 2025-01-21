package com.example.user.integration.user

import com.example.user.domain.history.HistoryRepository
import com.example.user.domain.user.UserRepository
import com.example.user.infrastructure.jpa.UserHistoryJpaRepository
import com.example.user.infrastructure.jpa.UserJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    private val restTemplate = TestRestTemplate()

    @Autowired
    private lateinit var repo: UserRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var historyJpa: UserHistoryJpaRepository

    @Autowired
    private lateinit var userJpa: UserJpaRepository

    @BeforeEach
    fun setUp() {
        userJpa.deleteAll()
        historyJpa.deleteAll()
    }

    @Test
    fun `request save user - success`() {
        val userId = "testId"
        val userName = "testName"
        val url = "http://localhost:$port/users"
        val req = mapOf("id" to userId, "name" to userName)
        val res = restTemplate.postForEntity(url, req, Map::class.java)

        assertEquals(HttpStatus.CREATED, res.statusCode)
        assertNotNull(res.body)
        assertEquals(userId, res.body!!["id"])
        assertEquals(userName, res.body!!["name"])

        val user = repo.find(userId)
        assertNotNull(user)
        assertEquals(userId, user.id)
        assertEquals(userName, user.name)

        val histories = historyJpa.findAll()
        assertTrue(histories.isNotEmpty())

        val history = histories.first()
        assertEquals(userName, history.newName)
    }

    @Test
    fun `request save user - fail`() {
        val url = "http://localhost:$port/users"

        val req1 = mapOf("id" to "", "name" to "testName")
        val res1 = restTemplate.postForEntity(url, req1, Map::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, res1.statusCode)

        val req2 = mapOf("id" to "testId", "name" to "")
        val res2 = restTemplate.postForEntity(url, req2, Map::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, res2.statusCode)

        val req3 = mapOf("id" to "testId", "name" to "tes")
        val res3 = restTemplate.postForEntity(url, req3, Map::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, res3.statusCode)
    }

}