package com.example.trackmybus.model

data class Location(

    val id: Long,

    val busId: Long,

    val latitude: Double,

    val longitude: Double,

    val timestamp: Long
)