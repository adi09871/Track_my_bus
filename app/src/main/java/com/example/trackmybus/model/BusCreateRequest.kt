package com.example.trackmybus.model

data class BusCreateRequest(

    val busNumber: String,

    val seatCapacity: Int,

    val routeName: String,

    val driverId: Long
)