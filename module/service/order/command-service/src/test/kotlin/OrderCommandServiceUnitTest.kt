import OrderCommand.*
import com.ecommerce.CommonException
import com.ecommerce.Order
import com.ecommerce.OrderCommandError.*
import com.ecommerce.OrderCommandService
import com.ecommerce.OrderItem
import com.ecommerce.OrderRepository
import com.ecommerce.OrderStatus
import com.ecommerce.PointCommandUseCase
import com.ecommerce.Product
import com.ecommerce.ProductCategory
import com.ecommerce.ProductQueryUseCase
import com.ecommerce.SaveOrderPort
import com.ecommerce.UserQueryUseCase
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OrderCommandServiceUnitTest {

    private lateinit var sut: OrderCommandService

    private val userId = UUID.randomUUID()
    private val orderId = UUID.randomUUID()
    private val category = ProductCategory(UUID.randomUUID(), "")

    private val productId1 = UUID.randomUUID()
    private val productPrice1 = 1000L
    private val productQuantity1 = 1L
    private val product1 = Product(productId1, category, "product1", productPrice1, productQuantity1)

    private val productId2 = UUID.randomUUID()
    private val productPrice2 = 2000L
    private val productQuantity2 = 2L
    private val product2 = Product(productId2, category, "product2", productPrice2, productQuantity2)

    val repo = mock<OrderRepository>()
    private val port = mock<SaveOrderPort>()
    private val userQuery = mock<UserQueryUseCase>()
    private val productQuery = mock<ProductQueryUseCase>()
    private val pointCommand = mock<PointCommandUseCase>()

    @BeforeEach
    fun setup() {
        sut = OrderCommandService(repo, port, userQuery, productQuery, pointCommand)

        whenever(repo.save(any())).thenAnswer { args -> args.arguments[0] }
        whenever(userQuery.isAvailableOrThrow(userId)).then {}

        whenever(productQuery.findAllOnSales(any())).thenReturn(emptyList())
        whenever(productQuery.findAllOnSales(listOf(productId1))).thenReturn(listOf(product1))
        whenever(productQuery.findAllOnSales(listOf(productId2))).thenReturn(listOf(product2))
        whenever(productQuery.findAllOnSales(argThat { this.toSet() == setOf(productId1, productId2) }))
            .thenReturn(listOf(product1, product2))
    }

    @Test
    fun `success - create Order`() {
        val items = mapOf(productId1 to 1L, productId2 to 1L)
        val command = CreateOrderCommand(userId, items)

        val result = sut.createOrder(command)
        assertEquals(items.size, result.items.size)
        assertEquals(productPrice1 + productPrice2, result.totalAmount)
        verify(userQuery, times(1)).isAvailableOrThrow(userId)
        verify(productQuery, times(1)).findAllOnSales(listOf(productId1, productId2))
        verify(repo, times(1)).save(any())
    }

    @Test
    fun `failure - illegal quantities`() {
        val items = mapOf(productId1 to 0L)
        val command = CreateOrderCommand(userId, items)

        val e = assertFailsWith<CommonException> { sut.createOrder(command) }
        assertEquals(ORDERED_QUANTITY_GTE_ONE.message, e.message)
    }

    @Test
    fun `failure - illegal productId`() {
        val items = mapOf(UUID.randomUUID() to 1L)
        val command = CreateOrderCommand(userId, items)

        val e = assertFailsWith<CommonException> { sut.createOrder(command) }
        assertEquals(ORDERED_PRODUCT_NOT_ORDERABLE.message, e.message)
    }

    @Test
    fun `success - proceed Order`() {
        val order = Order(orderId, userId)
            .apply { addOrderItem(OrderItem.create(productId1, 1000L, 2L)) }
        val command = ProceedOrderCommand(userId, orderId)

        whenever(repo.find(orderId)).thenReturn(order)

        val result = sut.proceedOrder(command)
        verify(port, times(1)).proceedPayment(userId, orderId)
        verify(pointCommand, times(1)).use(userId, order.totalAmount)
        verify(repo, times(1)).save(order)
        assertEquals(OrderStatus.PAID, result.status)
    }

    @Test
    fun `failure - not found order `() {
        val command = ProceedOrderCommand(userId, orderId)

        whenever(repo.find(orderId)).thenReturn(null)

        val e = assertFailsWith<CommonException> { sut.proceedOrder(command) }
        assertEquals(ORDER_NOT_FOUND.message, e.message)
    }

    @Test
    fun `success - cancel order`() {
        val order = Order(orderId, userId)
        val command = CancelOrderCommand(userId, orderId)

        whenever(repo.find(orderId)).thenReturn(order)

        val result = sut.cancelOrder(command)
        verify(port, times(1)).cancel(userId, orderId)
        verify(repo, times(1)).save(order)
        verify(pointCommand, times(1)).charge(userId, order.totalAmount)
        assertEquals(OrderStatus.CANCELLED, result.status)
    }

    @Test
    fun `failure- not found order`() {
        val command = CancelOrderCommand(userId, orderId)

        whenever(userQuery.isAvailableOrThrow(userId)).then {}
        whenever(repo.find(orderId)).thenReturn(null)

        val e = assertFailsWith<CommonException> { sut.cancelOrder(command) }
        assertEquals(ORDER_NOT_FOUND.message, e.message)
    }
}