package com.ecommerce.spec

import com.ecommerce.OrderCommandRequest.*
import com.ecommerce.OrderCommandResponse.*
import com.ecommerce.documentation.annotation.method.InternalServerErrorResponseSpec
import com.ecommerce.documentation.annotation.method.UserNotFoundResponseSpec
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Order", description = "Product Order API Spec")
interface OrderApiSpec {

    @UserNotFoundResponseSpec
    @InternalServerErrorResponseSpec
    fun postOrder(@RequestBody body: CreateOrder): OrderResponse

    @UserNotFoundResponseSpec
    @InternalServerErrorResponseSpec
    fun patchOrder(@RequestBody body: ProceedOrder): OrderResponse

    @UserNotFoundResponseSpec
    @InternalServerErrorResponseSpec
    fun deleteOrder(@RequestBody body: CancelOrder)

}