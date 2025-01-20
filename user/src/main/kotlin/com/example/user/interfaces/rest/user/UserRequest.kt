package com.example.user.interfaces.rest.user

class UserRequest {
    data class Create(
        val id: String,
        val name: String,

        // TODO : Not now, when spring-security added
        // val password: String
    )

    // NOTE : if there are some updatable properties are added, add here too
    data class Update(
        val id: String,
        val name: String,
    )
}