package com.example.trackmybus.model

data class LocationUpdateRequest(

    val busId: Long,

    val latitude: Double,

    val longitude: Double
)