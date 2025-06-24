package com.ecommerce

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "service_user",
    indexes = [
        Index(name = "IDX_USER_EMAIL", columnList = "email")
    ]
)
class UserCommandEntity(
    @Id
    val id: String = "",

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",
)