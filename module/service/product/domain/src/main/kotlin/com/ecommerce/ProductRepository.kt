package com.ecommerce

import java.util.UUID

interface ProductRepository {

    fun find(id: UUID): Product?
    fun findByIdList(productIdList: List<UUID>): List<Product>
    fun findAllByCategoryId(categoryId: UUID): List<Product>
    fun save(product: Product): Product
    fun remove(product: Product)

}