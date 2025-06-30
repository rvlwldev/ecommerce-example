package com.ecommerce

import com.ecommerce.documentation.annotation.field.UserId
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

class OrderCommandRequest {

    @Schema(name = "Create Order", description = "Create order with Products' id and each quantity")
    data class CreateOrder(
        @field:UserId
        val userId: UUID,

        @Schema(
            required = true,
            description = "Product Id to Quantity",
            example = """{ "9f21c631-7bde-4d67-891a-093cfa7deed1": 1, "d3a2c417-b7c6-4c52-9fc6-1e1e2c5bd9c4": 2 }"""
        )
        val items: Map<UUID, Long>
    )

    @Schema(name = "Proceed Order", description = "Proceed Order and pay by user ID")
    data class ProceedOrder(
        @field:UserId
        val userId: UUID,

        @Schema(
            required = true,
            description = "Order Number",
            example = """ { "number" : "20250101-12345678" } """
        )
        val number: String
    )

    @Schema(name = "Cancel Order")
    data class CancelOrder(
        @field:UserId
        val userId: UUID,

        @Schema(
            required = true,
            description = "Order Number",
            example = """ { "number" : "20250101-12345678" } """
        )
        val number: String
    )

}