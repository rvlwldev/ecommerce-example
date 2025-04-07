package com.ecommerce.interfaces.api

import com.ecommerce.service.Member
import java.util.UUID

class MemberResponse {
    data class MemberInfo(
        val uuid: UUID,
        val name: String,
        val email: String,
        val cash: Long
    ) {
        constructor(member: Member) : this(
            uuid = member.id,
            name = member.name,
            email = member.email,
            cash = member.cash,
        )
    }
}