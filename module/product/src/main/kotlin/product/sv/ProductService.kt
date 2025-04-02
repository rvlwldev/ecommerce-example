package product.sv

import org.springframework.stereotype.Service
import product.repository.ProductRepository
import product.vo.Product
import java.util.UUID

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun getProductById(id: UUID): Product? {
        return productRepository.findById(id).orElse(null)
    }

    fun createProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun updateProduct(id: UUID, product: Product): Product? {
        return if (productRepository.existsById(id)) {
            productRepository.save(product)
        } else {
            null
        }
    }

    fun deleteProduct(id: UUID): Boolean {
        return if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}