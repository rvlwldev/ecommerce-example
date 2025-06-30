package com.ecommerce

import java.util.UUID

interface UserQueryUseCase {

    fun isAvailableOrThrow(id: UUID)

}