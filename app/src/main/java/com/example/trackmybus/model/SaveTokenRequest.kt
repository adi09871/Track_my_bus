package com.example.trackmybus.model

data class SaveTokenRequest(

    val studentId: Long,

    val fcmToken: String
)