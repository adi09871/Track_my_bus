package com.aditya.trackmybus.model

data class DriverRegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

