import java.util.UUID

class MemberEvent {

    data class ChargeCash(val uuid: UUID, val amount: Long)
    data class UseCash(val uuid: UUID, val amount: Long)

}