package com.ecommerce

import java.time.ZonedDateTime

data class CommonErrorResponse(
    val code: String,
    val status: Int,
    val message: String,
    val timestamp: String = ZonedDateTime.now().toString()
)