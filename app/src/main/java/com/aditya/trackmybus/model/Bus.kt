package com.aditya.trackmybus.model

data class Bus(
    val id: Long,
    val busNumber: String,
    val routeName: String,
    val seatCapacity: Int,
    val driverId: Long,
    val currentOccupancy: Int,
    val totalStops: Int = 0,
    val isTripActive: Boolean
)