package com.example.user.domain.point

import com.example.user.core.exception.BizException
import com.example.user.domain.core.Audit
import com.example.user.domain.user.User
import com.example.user.domain.user.UserError
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_point")
class Point(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, optional = false)
    val user: User = User(),

    amount: Long = 0L,

    @Embedded
    val chargeAudit: Audit = Audit(),

    @Embedded
    val useAudit: Audit = Audit()
) {
    var amount = amount
        private set

    fun charge(amount: Long) {
        if (amount < 1) throw BizException(UserError.CASH_NOT_ALLOWED_ZERO)
        if (amount % 100 != 0L) throw BizException(UserError.CASH_INVALID_CHARGE_UNIT)

        this.amount += amount
        this.chargeAudit.update()
    }

    fun use(amount: Long) {
        if (amount < 1) throw BizException(UserError.CASH_NOT_ALLOWED_ZERO)
        if (this.amount - amount < 0L) throw BizException(UserError.CASH_NOT_ENOUGH_CASH)

        this.amount -= amount
        this.useAudit.update()
    }

}

