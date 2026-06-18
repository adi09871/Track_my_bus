package com.aditya.trackmybus.model

data class NotificationResponse(

    val id: Long,

    val studentId: Long,

    val title: String,

    val message: String,

    val createdAt: String,

    val isRead: Boolean
)