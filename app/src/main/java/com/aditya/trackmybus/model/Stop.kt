package com.aditya.trackmybus.model

data class Stop(
    val id: Long,
    val busId: Long,
    val stopName: String,
    val stopOrder: Int
)
