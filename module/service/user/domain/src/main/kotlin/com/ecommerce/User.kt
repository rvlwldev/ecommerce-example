package com.ecommerce

import java.util.UUID

class User(
    val id: UUID = UUID.randomUUID(),
    name: String = "",
    email: String = "",
    encryptedPassword: String = "",
) {
    var name = name
        private set

    var email: String = email
        private set

    var encryptedPassword: String = encryptedPassword
        private set

    constructor(name: String, email: String, encryptedPassword: String) : this() {
        validateEmail(email)

        this.name = name
        this.email = email
        this.encryptedPassword = encryptedPassword
    }

    fun validateEmail(email: String) =
        require(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)) { UserError.INVALID_EMAIL }

    fun updateEmail(newEmail: String) {
        validateEmail(newEmail)

        email = newEmail
    }

    fun validateName(name: String) = require(name.isNotEmpty()) { UserError.NOT_EMPTY_NAME }

    fun updateName(newName: String) {
        validateName(newName)

        name = newName
    }

}