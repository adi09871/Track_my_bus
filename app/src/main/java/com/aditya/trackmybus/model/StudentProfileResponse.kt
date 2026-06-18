package com.aditya.trackmybus.model

data class StudentProfileResponse(
    val id: Long,
    val name: String,
    val email: String,
    val collegeId: String,
    val busId: Long?
)
