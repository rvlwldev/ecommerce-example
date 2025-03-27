package product.repository

import org.springframework.data.jpa.repository.JpaRepository
import product.vo.Product
import java.util.UUID

interface ProductRepository : JpaRepository<Product, UUID>