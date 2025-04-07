package com.ecommerce.interfaces.api

class MemberRequest {
    data class Create(val name: String, val email: String, val password: String)
    data class Login(val email: String, val password: String)
    data class Delete(val password: String)
    data class Name(val name: String)
    data class ChargeCash(val amount: Long)
    data class UseCash(val amount: Long)
}



