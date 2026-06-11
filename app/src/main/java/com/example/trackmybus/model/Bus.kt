package com.example.trackmybus.model

data class Bus(
    val id: Long,
    val busNumber: String,
    val routeName: String,
    val seatCapacity: Int,
    val driverId: Long
)