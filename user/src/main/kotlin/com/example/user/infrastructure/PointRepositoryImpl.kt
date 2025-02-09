package com.example.user.infrastructure

import com.example.user.domain.point.Point
import com.example.user.domain.point.PointHistory
import com.example.user.domain.point.PointHistoryType
import com.example.user.domain.point.PointRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class PointRepositoryImpl(
    private val jpa: PointJpaRepository,
    private val historyJpa: PointHistoryJpaRepository
) : PointRepository {

    override fun find(userId: String) = jpa.findByUserId(userId).orElse(null)

    override fun save(point: Point, type: PointHistoryType?): Point {
        if (type != null)
            historyJpa.save(PointHistory(point.user.id, type, point))

        return jpa.save(point)
    }

}

interface PointJpaRepository : JpaRepository<Point, Long> {
    fun findByUserId(userId: String): Optional<Point>
}

interface PointHistoryJpaRepository : JpaRepository<PointHistory, Long> {

    fun findAllByIdOrderByCreatedAtDesc(id: String, pageable: Pageable): Page<PointHistory>

}