package com.ecommerce

import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {

    fun toDomain(entity: UserCommandEntity): User
    fun toEntity(domain: User): UserCommandEntity

}