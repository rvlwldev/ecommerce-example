package interfaces.event

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import service.MemberEvent
import service.MemberService

@Component
class MemberConsumer(private val service: MemberService) {

    @KafkaListener(topics = ["member.chargeCash"])
    fun onChargeCash(event: MemberEvent.ChargeCash) {
        service.chargeCash(event.uuid, event.amount)
    }

    @KafkaListener(topics = ["member.useCash"])
    fun onUseCash(event: MemberEvent.UseCash) {
        service.useCash(event.uuid, event.amount)
    }

}