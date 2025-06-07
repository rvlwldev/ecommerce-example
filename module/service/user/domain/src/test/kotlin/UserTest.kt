import com.ecommerce.User
import com.ecommerce.UserError
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class UserTest {

    val name = "test"
    val email = "test@test.com"
    val password = "pass"

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User(name, email, password)
    }

    @Test
    fun `success - name validation passes with valid inputs`() {
        assertDoesNotThrow { User("test", email, password) }
        assertDoesNotThrow { User("22", email, password) }
        assertDoesNotThrow { user.updateName("new") }
    }

    @Test
    fun `failure - name validation throws with invalid inputs`() {
        var ex = assertThrows<IllegalArgumentException> { User("", "test@test.com", "pass") }
        assertEquals(UserError.NOT_EMPTY_NAME, ex.message)

        ex = assertThrows<IllegalArgumentException> { User("1", "test@test.com", "pass") }
        assertEquals(UserError.INVALID_NAME_LENGTH, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateName("") }
        assertEquals(UserError.NOT_EMPTY_NAME, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateName("1") }
        assertEquals(UserError.INVALID_NAME_LENGTH, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateName(name) }
        assertEquals(UserError.DUPLICATE_NAME, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateName(user.name) }
        assertEquals(UserError.DUPLICATE_NAME, ex.message)
    }

    @Test
    fun `success - email validation passes with valid formats`() {
        assertDoesNotThrow { User(name, "test@test.com", password) }
        assertDoesNotThrow { User(name, "test@test.co.kr", password) }
        assertDoesNotThrow { User(name, "test@localhost", password) }
        assertDoesNotThrow { User(name, "test@[192.168.0.1]", password) }

        assertDoesNotThrow { user.updateEmail("test@test.co.kr") }
        assertDoesNotThrow { user.updateEmail("test@localhost") }
        assertDoesNotThrow { user.updateEmail("test@[192.168.0.1]") }
        assertDoesNotThrow { user.updateEmail("test.test+-123@test.test.co.kr") }
    }

    @Test
    fun `failure - email validation throws with invalid formats or duplicates`() {
        var ex = assertThrows<IllegalArgumentException> { User(name, "", password) }
        assertEquals(UserError.NOT_EMPTY_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { User(name, "test-email", password) }
        assertEquals(UserError.INVALID_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { User(name, "test@", password) }
        assertEquals(UserError.INVALID_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { User(name, "@test", password) }
        assertEquals(UserError.INVALID_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateEmail("not-an-email") }
        assertEquals(UserError.INVALID_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateEmail("not an email") }
        assertEquals(UserError.INVALID_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateEmail(email) }
        assertEquals(UserError.DUPLICATE_EMAIL, ex.message)

        ex = assertThrows<IllegalArgumentException> { user.updateEmail(user.email) }
        assertEquals(UserError.DUPLICATE_EMAIL, ex.message)
    }

    @Test
    fun `success - password update passes with non-empty value`() {
        assertDoesNotThrow { user.updatePassword("new") }
        assertDoesNotThrow { user.updatePassword("1") }
    }

    @Test
    fun `failure - password update throws when empty`() {
        val ex = assertThrows<IllegalArgumentException> { user.updatePassword("") }
        assertEquals(UserError.NOT_EMPTY_PASSWORD, ex.message)
    }
}