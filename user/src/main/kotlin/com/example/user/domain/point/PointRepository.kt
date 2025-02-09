package com.example.user.domain.point

interface PointRepository {

    fun find(id: String): Point?
    fun save(point: Point, type: PointHistoryType?): Point

}