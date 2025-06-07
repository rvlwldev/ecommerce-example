package com.ecommerce

object UserError {
    const val INVALID_NAME_LENGTH = "Name must be at least 2 characters long."
    const val NOT_EMPTY_NAME = "Name must not be empty."
    const val DUPLICATE_NAME = "New name is same as current name."

    const val INVALID_EMAIL = "Email format is invalid."
    const val NOT_EMPTY_EMAIL = "Email must not be empty."
    const val DUPLICATE_EMAIL = "New email is same as current email."

    const val NOT_EMPTY_PASSWORD = "Password is required."
}