package com.ecommerce.service

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Address() {
    @Id
    @GeneratedValue(jakarta.persistence.GeneratedValue.strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID()

    lateinit var memberId: UUID

    @Column(nullable = false)
    var country: String = ""
        private set

    @Column(nullable = false)
    var city: String = ""
        private set

    @Column(nullable = false)
    var street: String = ""
        private set

    @Column(nullable = false)
    var detail: String = ""
        private set

    constructor(memberId: UUID, country: String, city: String, street: String, detail: String) : this() {
        this.memberId = memberId
        this.country = country
        this.city = city
        this.street = street
        this.detail = detail
    }

    fun update(country: String, city: String, street: String, detail: String) {
        this.country = country
        this.city = city
        this.street = street
        this.detail = detail
    }

}

