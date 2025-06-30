package com.ecommerce

import java.util.UUID

class ProductCategory(
    val id: UUID = UUID.randomUUID(),
    val name: String
)