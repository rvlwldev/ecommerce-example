package com.ecommerce.service

class AddressCommand {
    data class Create(
        val country: String,
        val city: String,
        val street: String,
        val detail: String,
    )
}