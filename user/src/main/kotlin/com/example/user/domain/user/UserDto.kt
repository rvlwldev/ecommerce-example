package com.example.user.domain.user

class UserDto {
    data class Info(
        val id: String,
        val name: String
    ) {
        constructor(user: User) : this(
            id = user.id,
            name = user.name
        )
    }

    data class CashInfo(
        val amount: Long
    ) {
        constructor(user: User) : this(
            amount = user.cash
        )
    }
}