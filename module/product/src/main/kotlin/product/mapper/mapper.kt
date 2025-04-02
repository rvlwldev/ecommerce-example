package product.mapper

import product.dto.ProductDTO
import product.vo.Product

fun Product.toDTO(): ProductDTO {
    return ProductDTO(
        id = this.id,
        name = this.name,
        price = this.price,
        capacity = this.capacity,
        productStatus = this.productStatus
    )
}

fun ProductDTO.toEntity(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        capacity = this.capacity,
        productStatus = this.productStatus
    )
}