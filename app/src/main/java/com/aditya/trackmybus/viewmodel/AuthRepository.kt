package com.aditya.trackmybus.viewmodel

import com.aditya.trackmybus.model.DriverLoginRequest
import com.aditya.trackmybus.model.DriverRegisterRequest
import com.aditya.trackmybus.model.SaveTokenRequest
import com.aditya.trackmybus.model.StudentLoginRequest
import com.aditya.trackmybus.model.StudentRegisterRequest
import com.aditya.trackmybus.network.RetrofitInstance

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