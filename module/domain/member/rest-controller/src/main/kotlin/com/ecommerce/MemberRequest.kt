package com.ecommerce

class MemberRequest {

    data class Create(val name: String, val email: String, val password: String)
    data class Login(val email: String, val password: String)
    data class Cash(val uuid: String, val amount: Long)
    data class Withdraw(val password: String)

}