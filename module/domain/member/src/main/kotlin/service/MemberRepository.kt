package service

import java.util.UUID

interface MemberRepository {

    fun find(uuid: UUID): Member?
    fun find(email: String, encryptedPassword: String): Member?
    fun save(member: Member): Member
    fun delete(member: Member)

}