package com.example.user.domain.user

import com.example.user.core.exception.BizException
import com.example.user.domain.core.Audit
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "\"user\"", uniqueConstraints = [UniqueConstraint(name = "unique_user_id", columnNames = ["id"])])
class User(
    @Id
    val id: String = "",

    name: String = "",

    cash: Long = 0,

    @Embedded
    val audit: Audit = Audit(),
) {
    var name: String = name
        private set

    var cash: Long = cash
        private set

    constructor(id: String, name: String) : this(id = id, name = name, cash = 0)

    fun chargeCash(amount: Long) {
        if (amount < 1) throw BizException(UserError.CASH_NOT_ALLOWED_ZERO)
        if (amount % 100 != 0L) throw BizException(UserError.CASH_INVALID_CHARGE_UNIT)

        cash += amount
    }

    fun useCash(amount: Long) {
        if (amount < 1) throw BizException(UserError.CASH_NOT_ALLOWED_ZERO)
        if (cash - amount < 0L) throw BizException(UserError.CASH_NOT_ENOUGH_CASH)

        cash -= amount
    }

    fun changeName(newName: String) {
        if (name == newName)
            throw BizException(UserError.NAME_DUPLICATED_UPDATE)

        if (!newName.matches(Regex("^[a-zA-Z가-힣]+$")) || newName.isBlank())
            throw BizException(UserError.NAME_INVALID)

        name = newName
        audit.update()
    }

    fun toInfo() = UserInfo(this)
}