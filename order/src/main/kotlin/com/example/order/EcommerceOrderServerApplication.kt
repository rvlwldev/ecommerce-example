package com.example.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcommerceOrderServerApplication

fun main(args: Array<String>) {
	runApplication<EcommerceOrderServerApplication>(*args)
}
