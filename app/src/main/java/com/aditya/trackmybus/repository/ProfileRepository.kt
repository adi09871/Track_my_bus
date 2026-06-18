package com.aditya.trackmybus.repository

import com.aditya.trackmybus.network.RetrofitInstance

class ProfileRepository {
    suspend fun getStudentProfile(studentId: Long) = 
        RetrofitInstance.api.getStudentProfile(studentId)

    suspend fun getDriverById(driverId: Long) = 
        RetrofitInstance.api.getDriverById(driverId)

    suspend fun getBusById(busId: Long) = 
        RetrofitInstance.api.getBusById(busId)
}
