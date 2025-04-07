package com.ecommerce.interfaces.event

import com.ecommerce.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MemberConsumer(private val service: MemberService, private val mapper: ObjectMapper) {

    @KafkaListener(topics = ["member.cash"], groupId = "member")
    fun onCashEvent(record: ConsumerRecord<String, String>) {
        val event = mapper.readValue(record.value(), MemberEvent.Cash::class.java)

        when (event.type) {
            MemberEvent.CashEventType.CHARGE -> service.chargeCash(event.uuid, event.amount)
            MemberEvent.CashEventType.USE -> service.useCash(event.uuid, event.amount)
            // TODO : else -> Logging Undefined Event
        }
    }

}