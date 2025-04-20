package com.ecommerce

import java.util.UUID

interface MemberRepository {

    fun find(uuid: UUID): Member?
    fun find(email: String): Member?
    fun save(member: Member): Member
    fun delete(member: Member)

}