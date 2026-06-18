package com.aditya.trackmybus.model

data class SaveTokenRequest(

    val studentId: Long,

    val fcmToken: String
)