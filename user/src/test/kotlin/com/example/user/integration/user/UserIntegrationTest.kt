package com.example.user.integration.user

import com.example.user.infrastructure.jpa.UserJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var jpa: UserJpaRepository

    private val restTemplate = RestTemplate()

    @BeforeEach
    fun setUp() {
        jpa.deleteAll()
    }

    @Test
    fun `request save user - success`() {
        val url = "http://localhost:$port/users"
        val req = mapOf("id" to "testId", "name" to "testName")
        val res = restTemplate.postForEntity(url, req, Map::class.java)

        assertEquals(HttpStatus.CREATED, res.statusCode)
        assertNotNull(res.body)
        assertEquals("testId", res.body!!["id"])
        assertEquals("testName", res.body!!["name"])

        val user = jpa.findById("testId").orElse(null)
        assertNotNull(user)
        assertEquals("testId", user.id)
        assertEquals("testName", user.name)
    }

    @Test
    fun `request save user - fail`() {
        val url = "http://localhost:$port/users"

        val req1 = mapOf("id" to "", "name" to "testName")
        val res1 = assertThrows<HttpClientErrorException> {
            restTemplate.postForEntity(url, req1, Map::class.java)
        }
        assertEquals(HttpStatus.BAD_REQUEST, res1.statusCode)

        val req2 = mapOf("id" to "testId", "name" to "")
        val res2 = assertThrows<HttpClientErrorException> {
            restTemplate.postForEntity(url, req2, Map::class.java)
        }
        assertEquals(HttpStatus.BAD_REQUEST, res2.statusCode)

        val req3 = mapOf("id" to "testId", "name" to "tes")
        val res3 = assertThrows<HttpClientErrorException> {
            restTemplate.postForEntity(url, req3, Map::class.java)
        }
        assertEquals(HttpStatus.BAD_REQUEST, res3.statusCode)
    }

}