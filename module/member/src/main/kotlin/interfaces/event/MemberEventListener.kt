package interfaces.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import service.MemberEvent
import service.MemberService

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