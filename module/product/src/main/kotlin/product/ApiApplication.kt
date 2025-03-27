package product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["product.repository"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}