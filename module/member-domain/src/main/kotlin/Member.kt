import java.util.UUID

data class Member(
    val id: UUID,

    var name: String,
    var email: String,
    var password: String,
    var cash: Long = 0
)