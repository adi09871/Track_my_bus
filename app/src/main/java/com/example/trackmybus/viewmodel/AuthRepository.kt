package com.example.trackmybus.viewmodel

import com.example.trackmybus.model.DriverLoginRequest
import com.example.trackmybus.model.DriverRegisterRequest
import com.example.trackmybus.model.SaveTokenRequest
import com.example.trackmybus.model.StudentLoginRequest
import com.example.trackmybus.model.StudentRegisterRequest
import com.example.trackmybus.network.RetrofitInstance

class AuthRepository {

    suspend fun studentRegister(
        request: StudentRegisterRequest
    ) =
        RetrofitInstance.api.studentRegister(
            request
        )

    suspend fun studentLogin(
        request: StudentLoginRequest
    ) =
        RetrofitInstance.api.studentLogin(
            request
        )
    suspend fun driverLogin(
        request: DriverLoginRequest
    ) =
        RetrofitInstance.api.driverLogin(
            request
        )
    suspend fun driverRegister(
        request: DriverRegisterRequest
    ) =
        RetrofitInstance.api.driverRegister(
            request
        )
    suspend fun saveToken(
        request: SaveTokenRequest
    ) =
        RetrofitInstance.api.saveToken(
            request
        )

    suspend fun getNotifications(
        studentId: Long
    ) =
        RetrofitInstance.api.getNotifications(
            studentId
        )
}