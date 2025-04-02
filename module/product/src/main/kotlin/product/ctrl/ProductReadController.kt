package product.ctrl

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import product.sv.ProductService
import product.vo.Product
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductReadController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: UUID): ResponseEntity<Product> {
        val product = productService.getProductById(id)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}