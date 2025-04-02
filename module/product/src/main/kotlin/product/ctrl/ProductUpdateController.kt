package product.ctrl

import org.springframework.http.HttpStatus
import product.sv.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import product.vo.Product
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductUpdateController(private val productService: ProductService) {

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: UUID, @RequestBody product: Product): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(id, product)
        return if (updatedProduct != null) {
            ResponseEntity.ok(updatedProduct)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}