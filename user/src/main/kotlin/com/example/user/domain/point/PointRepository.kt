package com.example.user.domain.point

interface PointRepository {

    fun find(id: Long): Point
    fun save(point: Point): Point

}