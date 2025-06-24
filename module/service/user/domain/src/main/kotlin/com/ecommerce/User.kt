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

    var email = email
        private set

    var password = password
        private set

    constructor(name: String, email: String, password: String) : this() {
        validateName(name)
        validateEmail(email)
        validatePassword(password)

        this.name = name
        this.email = email
        this.password = password
    }

    private inline fun validateOrThrow(condition: Boolean, error: () -> UserError) {
        if (!condition) throw UserException(error())
    }

    private fun validateName(name: String) {
        validateOrThrow(name.isNotEmpty()) { UserError.NOT_EMPTY_NAME }
        validateOrThrow(name.length >= 2) { UserError.INVALID_NAME_LENGTH }
        validateOrThrow(name != this.name) { UserError.DUPLICATE_NAME }
    }

    fun updateName(name: String) {
        validateName(name)
        this.name = name
    }

    private fun validateEmail(email: String) {
        val regex = Regex("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+|\\[[0-9]{1,3}(\\.[0-9]{1,3}){3}])$")
        validateOrThrow(email.isNotEmpty()) { UserError.NOT_EMPTY_EMAIL }
        validateOrThrow(regex.matches(email)) { UserError.INVALID_EMAIL }
        validateOrThrow(email != this.email) { UserError.DUPLICATE_EMAIL }
    }

    fun updateEmail(newEmail: String) {
        validateEmail(newEmail)
        this.email = newEmail
    }

    private fun validatePassword(password: String) {
        validateOrThrow(password.isNotEmpty()) { UserError.NOT_EMPTY_PASSWORD }
    }

    fun updatePassword(password: String) {
        validatePassword(password)
        this.password = password
    }

}