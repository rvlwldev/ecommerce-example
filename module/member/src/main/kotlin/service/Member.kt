package service

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Member(
    @Id
    val id: UUID = UUID.randomUUID(),

    name: String = "",
    email: String = "",
    password: String = "",
    cash: Long = 0
) {
    var name = name
        protected set

    @Column(unique = true)
    var email = email
        protected set
    var password = password
        protected set
    var cash = cash
        protected set
}