package com.ecommerce

class UserResponse {

    data class Member(val uuid: String, val email: String, val name: String, val cash: Long) {
        constructor(member: com.ecommerce.User) : this(
            uuid = member.id.toString(),
            email = member.email,
            name = member.name,
            cash = member.cash
        )
    }

}