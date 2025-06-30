package com.ecommerce

import java.util.UUID

interface PointCommandUseCase {

    fun use(userId: UUID, amount: Long)
    fun charge(userId: UUID, amount: Long)

}