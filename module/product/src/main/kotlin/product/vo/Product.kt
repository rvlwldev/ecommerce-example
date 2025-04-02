package product.vo

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import product.eunm.EnumProductStatus
import java.util.UUID

@Entity
@Table(name = "product")
data class Product(
    @Id
    val id: UUID,
    val name: String,
    val price: Int,
    val capacity: Int,
    val productStatus: EnumProductStatus
) {
    fun isOrderable(): Boolean {
        return productStatus == EnumProductStatus.ENABLE
    }
}