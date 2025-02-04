package com.example.user.domain.point

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(private val repo: PointRepository) {

    fun find(id: Long) =
        repo.find(id).toInfo()

    @Transactional
    fun charge(id: Long, amount: Long): PointInfo {
        val point = repo.find(id)

        point.charge(amount)

        return repo.save(point).toInfo()
    }

    @Transactional
    fun use(id: Long, amount: Long): PointInfo {
        val point = repo.find(id)

        point.use(amount)

        return repo.save(point).toInfo()
    }

}