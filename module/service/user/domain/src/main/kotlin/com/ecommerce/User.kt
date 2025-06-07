package com.ecommerce

import java.util.UUID
import kotlin.text.Regex

class User(
    val id: UUID = UUID.randomUUID(),
    name: String = "",
    email: String = "",
    password: String = "",
) {
    var name = name
        private set

    var email: String = email
        private set

    var password: String = password
        private set

    constructor(name: String, email: String, password: String) : this() {
        validateName(name)
        validateEmail(email)
        validatePassword(password)

        this.name = name
        this.email = email
        this.password = password
    }

    fun validateName(name: String) {
        require(name.isNotEmpty()) { UserError.NOT_EMPTY_NAME }
        require(name.length >= 2) { UserError.INVALID_NAME_LENGTH }
        require(name != this.name) { UserError.DUPLICATE_NAME }
    }

    fun updateName(name: String) {
        validateName(name)
        this.name = name
    }

    fun validateEmail(email: String) {
        val regex = Regex("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+|\\[[0-9]{1,3}(\\.[0-9]{1,3}){3}])$")

        require(email.isNotEmpty()) { UserError.NOT_EMPTY_EMAIL }
        require(regex.matches(email)) { UserError.INVALID_EMAIL }
        require(email != this.email) { UserError.DUPLICATE_EMAIL }
    }

    fun updateEmail(newEmail: String) {
        validateEmail(newEmail)
        email = newEmail
    }

    fun validatePassword(password: String) {
        require(password.isNotEmpty()) { UserError.NOT_EMPTY_PASSWORD }
    }

    fun updatePassword(password: String) {
        validatePassword(password)
        this.password = password
    }

}