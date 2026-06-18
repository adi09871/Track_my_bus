package com.example.trackmybus.repository

import com.example.trackmybus.network.RetrofitInstance

class ProfileRepository {
    suspend fun getStudentProfile(studentId: Long) = 
        RetrofitInstance.api.getStudentProfile(studentId)

    suspend fun getDriverById(driverId: Long) = 
        RetrofitInstance.api.getDriverById(driverId)

    suspend fun getBusById(busId: Long) = 
        RetrofitInstance.api.getBusById(busId)
}
