package com.ecommerce

import com.ecommerce.OrderCommand.*
import com.ecommerce.OrderCommandRequest.*
import com.ecommerce.OrderCommandResponse.*
import com.ecommerce.spec.OrderApiSpec
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/order")
class OrderCommandRestController(val service: OrderCommandUseCase) : OrderApiSpec {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun postOrder(body: CreateOrder) =
        with(service.createOrder(CreateOrderCommand(body.userId, body.items))) { OrderResponse(this) }

    @PatchMapping
    override fun patchOrder(body: ProceedOrder) =
        with(service.proceedOrder(ProceedOrderCommand(body.userId, body.number))) { OrderResponse(this) }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteOrder(body: CancelOrder) {
        with(body) { service.cancelOrder(CancelOrderCommand(userId, number)) }
    }

}