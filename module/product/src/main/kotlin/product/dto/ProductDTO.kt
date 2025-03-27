package product.dto

import product.eunm.EnumProductStatus
import java.util.UUID

data class ProductDTO(
    val id: UUID,
    val name: String,
    val price: Int,
    val capacity: Int,
    val productStatus: EnumProductStatus
)