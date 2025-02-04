package com.example.user.domain.user

import com.example.user.core.exception.BizException
import com.example.user.domain.core.Audit
import com.example.user.domain.point.Point
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kotlin.text.Regex
import kotlin.text.isBlank
import kotlin.text.matches

@Entity
@Table(name = "\"user\"", uniqueConstraints = [UniqueConstraint(name = "unique_user_id", columnNames = ["id"])])
class User(
    @Id
    val id: String = "",

    name: String = "",

    val point: Point = Point(),

    @Embedded
    val audit: Audit = Audit(),
) {
    var name: String = name
        private set

    constructor(id: String, name: String) : this(id = id, name = name, point = Point()) {
        if (id.length < 4 || id.isBlank() || !id.matches(Regex("^[a-zA-Z가-힣]+$")))
            throw BizException(UserError.USER_INVALID_ID)

        if (name.length < 4 || !name.matches(Regex("^[a-zA-Z가-힣]+$")) || name.isBlank())
            throw BizException(UserError.NAME_INVALID)
    }

    fun changeName(newName: String) {
        if (name == newName)
            throw BizException(UserError.NAME_DUPLICATED_UPDATE)

        if (newName.length < 4 || !newName.matches(Regex("^[a-zA-Z가-힣]+$")) || newName.isBlank())
            throw BizException(UserError.NAME_INVALID)

        name = newName
        audit.update()
    }

}