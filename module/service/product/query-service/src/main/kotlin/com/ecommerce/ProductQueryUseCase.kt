package com.ecommerce

import java.util.UUID

interface ProductQueryUseCase {

    fun find(id: UUID): Product
    fun findAllOnSales(ids: List<UUID>): List<Product>
    fun findAllByCategoryId(id: UUID): List<Product>

}