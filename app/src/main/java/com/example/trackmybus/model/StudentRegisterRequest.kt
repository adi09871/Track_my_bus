package com.example.trackmybus.model

data  class StudentRegisterRequest (
    val name: String,
    val email: String,
    val password: String,
    val collegeId: String,
    val busId: Long
    )