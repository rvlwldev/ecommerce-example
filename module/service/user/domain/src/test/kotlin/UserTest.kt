import com.ecommerce.User
import com.ecommerce.UserException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

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
        assertThrows<UserException> { User("", "test@test.com", "pass") }
        assertThrows<UserException> { User("1", "test@test.com", "pass") }
        assertThrows<UserException> { user.updateName("") }
        assertThrows<UserException> { user.updateName("1") }
        assertThrows<UserException> { user.updateName(name) }
        assertThrows<UserException> { user.updateName(user.name) }
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
        assertThrows<UserException> { User(name, "", password) }
        assertThrows<UserException> { User(name, "test-email", password) }
        assertThrows<UserException> { User(name, "test@", password) }
        assertThrows<UserException> { User(name, "@test", password) }
        assertThrows<UserException> { user.updateEmail("not-an-email") }
        assertThrows<UserException> { user.updateEmail("not an email") }
        assertThrows<UserException> { user.updateEmail(email) }
        assertThrows<UserException> { user.updateEmail(user.email) }
    }

    @Test
    fun `success - password update passes with non-empty value`() {
        assertDoesNotThrow { user.updatePassword("new") }
        assertDoesNotThrow { user.updatePassword("1") }
    }

    @Test
    fun `failure - password update throws when empty`() {
        assertThrows<UserException> { user.updatePassword("") }
    }
    
}