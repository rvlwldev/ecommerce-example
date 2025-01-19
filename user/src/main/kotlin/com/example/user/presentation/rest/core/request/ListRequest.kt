package com.example.user.presentation.rest.core.request

import org.springframework.format.annotation.NumberFormat

data class ListRequest(
    @field:NumberFormat
    val number: Int = 1,
    @field:NumberFormat
    val size: Int = 10,
)