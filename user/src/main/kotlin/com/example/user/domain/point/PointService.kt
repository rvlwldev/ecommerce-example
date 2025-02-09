package com.example.user.domain.point

import com.example.user.core.exception.BizException
import com.example.user.domain.user.UserError
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(private val repo: PointRepository) {

    fun find(userId: String): PointDto.Info {
        val point = repo.find(userId) ?: throw BizException(UserError.USER_NOT_FOUND)

        return point.toInfo()
    }

    @Transactional
    fun charge(userId: String, amount: Long): PointDto.Info {
        val point = repo.find(userId) ?: throw BizException(UserError.USER_NOT_FOUND)

        point.charge(amount)

        return repo.save(point, PointHistoryType.CHARGE).toInfo()
    }

    @Transactional
    fun use(userId: String, amount: Long): PointDto.Info {
        val point = repo.find(userId) ?: throw BizException(UserError.USER_NOT_FOUND)

        point.use(amount)

        return repo.save(point, PointHistoryType.CHARGE).toInfo()
    }

}