import com.ecommerce.Point
import com.ecommerce.PointError
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals

class PointTest {

    private lateinit var point: Point
    private val userId = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        point = Point(userId = userId)
    }

    @Test
    fun `success - initial point amount should be zero`() {
        assertEquals(0, point.amount)
    }

    @Test
    fun `success - charge increases point correctly with valid amount`() {
        assertDoesNotThrow { point.charge(10) }
        assertEquals(10, point.amount)

        point.charge(20)
        assertEquals(30, point.amount)
    }

    @Test
    fun `success - charge multiple times accumulates amount correctly`() {
        point.charge(10)
        point.charge(20)
        point.charge(30)

        assertEquals(60, point.amount)
    }

    @Test
    fun `failure - charge throws when amount is zero or negative`() {
        var ex = assertThrows<IllegalArgumentException> { point.charge(0) }
        assertEquals(PointError.INVALID_POINT_AMOUNT.name, ex.message)

        ex = assertThrows<IllegalArgumentException> { point.charge(-10) }
        assertEquals(PointError.INVALID_POINT_AMOUNT.name, ex.message)
    }

    @Test
    fun `failure - charge throws when amount is not multiple of charge unit`() {
        val ex = assertThrows<IllegalArgumentException> { point.charge(15) }

        assertEquals(PointError.INVALID_CHARGE_AMOUNT_UNIT.name, ex.message)
    }

    @Test
    fun `success - use deducts point correctly with sufficient balance`() {
        point.charge(50)

        assertDoesNotThrow { point.use(10) }
        assertEquals(40, point.amount)

        point.use(40)
        assertEquals(0, point.amount)
    }

    @Test
    fun `success - use multiple times deducts amount correctly`() {
        point.charge(50)
        point.use(10)
        point.use(10)

        assertEquals(30, point.amount)
    }

    @Test
    fun `success - charge and use repeatedly keeps correct state`() {
        point.charge(50)
        point.use(10)
        point.charge(20)
        point.use(30)

        assertEquals(30, point.amount)
    }

    @Test
    fun `failure - use throws when amount is zero or negative`() {
        var ex = assertThrows<IllegalArgumentException> { point.use(0) }
        assertEquals(PointError.INVALID_POINT_AMOUNT.name, ex.message)

        ex = assertThrows<IllegalArgumentException> { point.use(-10) }
        assertEquals(PointError.INVALID_POINT_AMOUNT.name, ex.message)
    }

    @Test
    fun `failure - use throws when amount exceeds current point`() {
        point.charge(30)

        val ex = assertThrows<IllegalArgumentException> { point.use(50) }
        assertEquals(PointError.NOT_ENOUGH_POINT.name, ex.message)
    }
}