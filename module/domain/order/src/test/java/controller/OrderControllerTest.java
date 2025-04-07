package controller;

import com.ecommerce.controller.OrderController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.ecommerce.repo.OrderRepository;
import com.ecommerce.service.OrderService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private OrderService orderService;
    private OrderController orderController;
    private OrderRepository repo;

    private UUID orderID;
    private UUID memberID;
    private UUID addressID;
    private List<OrderItem> orderItems;
    private Order order;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();

        repo = new OrderRepository() {
            @Override
            public Order save(Order order) {
                return order;
            }
        };

        orderService = new OrderService(repo);
        orderController = new OrderController(orderService);

        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        orderID = UUID.randomUUID();
        memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        addressID = UUID.fromString("123e4567-e89b-12d3-a456-556642440000");

        orderItems = List.of(
                new OrderItem(UUID.randomUUID(), 2, 10000),
                new OrderItem(UUID.randomUUID(), 1, 5000)
        );

//        order = new Order(orderID, memberID, addressID, orderItems, null);
    }

    @Test
    @DisplayName("create order test")
    void createOrder() throws Exception {
        OrderRequest request = new OrderRequest(memberID, addressID, orderItems);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(memberID.toString()))
                .andExpect(jsonPath("$.addressId").value(addressID.toString()))
                .andExpect(jsonPath("$.orderItems.length()").value(orderItems.size()))
                .andExpect(jsonPath("$.status").value(OrderStatus.PAID.toString()));
    }
}