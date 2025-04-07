package com.ecommerce.interfaces.event

import java.util.UUID


class MemberEvent {

    enum class CashEventType { CHARGE, USE }
    data class Cash(
        val type: CashEventType,
        val uuid: UUID,
        val amount: Long
    )

}

