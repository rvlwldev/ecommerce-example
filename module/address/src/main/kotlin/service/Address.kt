package service

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class Address() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    lateinit var member: Member

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    var country: String = ""
        private set

    @Column(nullable = false)
    var city: String = ""
        private set

    @Column(nullable = false)
    var street: String = ""
        private set

    @Column(nullable = false)
    var detail: String = ""
        private set

    constructor(memberId: UUID, country: String, city: String, street: String, detail: String) : this() {
        this.member = Member(memberId)
        this.country = country
        this.city = city
        this.street = street
        this.detail = detail
    }

    fun update(country: String, city: String, street: String, detail: String) {
        this.country = country
        this.city = city
        this.street = street
        this.detail = detail
    }

}

