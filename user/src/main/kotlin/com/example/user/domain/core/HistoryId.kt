package com.example.user.domain.core

import java.io.Serializable


data class HistoryId(
    val seq: Long = 0,
    val id: String = "",
) : Serializable