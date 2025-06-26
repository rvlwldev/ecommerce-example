package com.ecommerce

enum class OrderCommandError(
    override val code: String,
    override val status: Int,
    override val message: String
) : CommonError {

    INVALID_ORDER_COMMAND("ORDER_000", 500, "Invalid order request."),
    NOT_FOUND_ORDERED_PRODUCT("ORDER_001", 404, "Ordered product not found."),
    ORDERED_PRODUCT_DUPLICATED("ORDER_002", 409, "Duplicate product in order."),
    ORDERED_PRODUCT_NOT_ORDERABLE("ORDER_003", 400, "Order contains an unsellable product."),
    ORDERED_QUANTITY_GTE_ONE("ORDER_004", 400, "Quantity must be at least one."),
    ALREADY_PAID_ORDER("ORDER_005", 400, "Order has already been paid."),
    ORDER_NOT_CANCELABLE("ORDER_006", 400, "Order cannot be cancelled in its current status."),
    ORDER_NOT_FOUND("ORDER_404", 404, "Order Not Found");

    companion object {
        private val map = mapOf(
            OrderError.ORDERED_PRODUCT_DUPLICATED to ORDERED_PRODUCT_DUPLICATED,
            OrderError.ALREADY_PAID_ORDER to ALREADY_PAID_ORDER,
            OrderError.ORDER_NOT_CANCELABLE to ORDER_NOT_CANCELABLE,
            OrderError.ORDERED_PRODUCT_NOT_ORDERABLE to ORDERED_PRODUCT_NOT_ORDERABLE,
            OrderError.ORDERED_QUANTITY_GTE_ONE to ORDERED_QUANTITY_GTE_ONE,
            OrderError.NOT_FOUND_ORDERED_PRODUCT to NOT_FOUND_ORDERED_PRODUCT
        )

        fun toCommonException(domainError: OrderError): CommonException =
            CommonException(map[domainError] ?: INVALID_ORDER_COMMAND)
    }
}