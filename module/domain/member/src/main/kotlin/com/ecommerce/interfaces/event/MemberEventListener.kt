package com.ecommerce.interfaces.event

import com.ecommerce.service.MemberEvent
import com.ecommerce.service.MemberService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class MemberEventListener(private val service: MemberService) {

    @EventListener
    fun onChargeCash(event: MemberEvent.ChargeCash) {
        service.chargeCash(event.uuid, event.amount)
    }

    @EventListener
    fun onUseCash(event: MemberEvent.UseCash) {
        service.useCash(event.uuid, event.amount)
    }

}