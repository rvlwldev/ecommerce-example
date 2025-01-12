package com.example.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcommerceProductServerApplication

fun main(args: Array<String>) {
	runApplication<EcommerceProductServerApplication>(*args)
}
