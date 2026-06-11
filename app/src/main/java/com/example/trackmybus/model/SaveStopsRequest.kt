package com.example.trackmybus.model

data class SaveStopsRequest(
    val busId: Long,
    val stops: List<String>
)