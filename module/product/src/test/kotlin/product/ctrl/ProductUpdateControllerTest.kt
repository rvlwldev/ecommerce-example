package com.example.myecommerce.product

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.hamcrest.Matchers.*
import product.vo.Product

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `getAllProducts should return a list of products`() {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize<Any>(0))) // 초기 데이터가 없으므로 사이즈 0
    }

    @Test
    fun `createProduct should create a new product`() {
        val product = Product(name = "Test Product", description = "Test Description", price = 19.99)
        mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", `is`("Test Product")))
            .andExpect(jsonPath("$.description", `is`("Test Description")))
            .andExpect(jsonPath("$.price", `is`(19.99)))
    }

    @Test
    fun `getProductById should return a product by id`() {
        // 먼저 상품을 생성
        val product = Product(name = "Test Product", description = "Test Description", price = 19.99)
        val result = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        ).andReturn()

        // 생성된 상품의 ID를 가져옴
        val responseContent = result.response.contentAsString
        val createdProduct = objectMapper.readValue(responseContent, Product::class.java)
        val productId = createdProduct.id

        // 가져온 ID로 상품을 조회
        mockMvc.perform(get("/products/${productId}"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", `is`("Test Product")))
            .andExpect(jsonPath("$.description", `is`("Test Description")))
            .andExpect(jsonPath("$.price", `is`(19.99)))
    }

    @Test
    fun `updateProduct should update an existing product`() {
        // 먼저 상품을 생성
        val product = Product(name = "Test Product", description = "Test Description", price = 19.99)
        val result = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        ).andReturn()

        // 생성된 상품의 ID를 가져옴
        val responseContent = result.response.contentAsString
        val createdProduct = objectMapper.readValue(responseContent, Product::class.java)
        val productId = createdProduct.id

        // 상품 업데이트
        val updatedProduct = Product(id = productId, name = "Updated Product", description = "Updated Description", price = 29.99)
        mockMvc.perform(
            put("/products/${productId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", `is`("Updated Product")))
            .andExpect(jsonPath("$.description", `is`("Updated Description")))
            .andExpect(jsonPath("$.price", `is`(29.99)))
    }

    @Test
    fun `deleteProduct should delete an existing product`() {
        // 먼저 상품을 생성
        val product = Product(name = "Test Product", description = "Test Description", price = 19.99)
        val result = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        ).andReturn()

        // 생성된 상품의 ID를 가져옴
        val responseContent = result.response.contentAsString
        val createdProduct = objectMapper.readValue(responseContent, Product::class.java)
        val productId = createdProduct.id

        // 상품 삭제
        mockMvc.perform(delete("/products/${productId}"))
            .andExpect(status().isNoContent)

        // 삭제 후 상품이 없는지 확인
        mockMvc.perform(get("/products/${productId}"))
            .andExpect(status().isNotFound)
    }
}