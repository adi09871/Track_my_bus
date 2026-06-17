package com.example.trackmybus.repository

import com.example.trackmybus.network.RetrofitInstance

class ProfileRepository {
    suspend fun getStudentProfile(studentId: Long) = 
        RetrofitInstance.api.getStudentProfile(studentId)
}
