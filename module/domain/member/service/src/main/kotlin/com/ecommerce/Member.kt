package com.ecommerce

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Member(
    @Id
    val id: UUID = UUID.randomUUID()
) {
    @Column(nullable = false)
    var name: String = ""
        private set

    @Column(nullable = false, unique = true)
    var email: String = ""
        private set

    @Column(nullable = false)
    var password: String = ""
        private set

    @Column(nullable = false)
    var cash: Long = 0
        private set

    constructor(name: String, email: String, password: String) : this() {
        validateEmail(email)

        this.name = name
        this.email = email
        this.password = password
    }

    private fun validateEmail(email: String) {
        if (!Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email))
            throw IllegalArgumentException()
    }

    fun updateName(newName: String) {
        if (newName.isEmpty())
            throw IllegalArgumentException()
        this.name = newName
    }

    fun updateEmail(newEmail: String) {
        validateEmail(newEmail)
        this.email = newEmail
    }

    fun updatePassword(oldPassword: String, newPassword: String) {
        if (this.password != oldPassword)
            throw IllegalArgumentException()
        this.password = newPassword
    }

    fun decreaseCash(amount: Long) {
        if (amount > this.cash)
            throw IllegalStateException()
        this.cash -= amount
    }

    fun increaseCash(amount: Long) {
        this.cash += amount
    }

}