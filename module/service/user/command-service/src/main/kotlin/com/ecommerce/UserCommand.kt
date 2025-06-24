package com.ecommerce

class UserCommand {

    data class Create(val email: String, val name: String, val password: String)

    data class UpdateName(val id: String, val name: String)
    data class UpdateEmail(val id: String, val email: String)
    data class UpdatePassword(val id: String, val oldPassword: String, val newPassword: String)

    data class Remove(val id: String)

}


