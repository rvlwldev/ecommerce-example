package com.example.user.domain.user

import com.example.user.core.exception.BizException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "unique_user_id", columnNames = ["id"])])
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uuid: Long = 0,

    @Column(nullable = false, unique = true)
    val id: String = "",

    var name: String = "",

    var cash: Long = 0
) {
    constructor() : this(0)
    constructor(id: String, name: String) : this(uuid = 0, id = id, name = name, cash = 0)

    fun useCash(amount: Long) {
        if (cash - amount < 0L) throw BizException(UserError.NOT_ENOUGH_CASH)
        cash -= amount
    }

    fun chargeCash(amount: Long) {
        if (amount % 100 != 0L) throw BizException(UserError.INVALID_CHARGE_UNIT)
        cash += amount
    }

    fun changeName(newName: String) {
        if (!newName.matches(Regex("^[a-zA-Z가-힣]+$")) || newName.isBlank())
            throw BizException(UserError.INVALID_NAME)
        name = newName
    }
}