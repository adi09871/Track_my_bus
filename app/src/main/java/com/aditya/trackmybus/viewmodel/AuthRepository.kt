package com.aditya.trackmybus.viewmodel

import com.aditya.trackmybus.model.DriverLoginRequest
import com.aditya.trackmybus.model.DriverLoginResponse
import com.aditya.trackmybus.model.DriverRegisterRequest
import com.aditya.trackmybus.model.DriverRegisterResponse
import com.aditya.trackmybus.model.NotificationResponse
import com.aditya.trackmybus.model.SaveTokenRequest
import com.aditya.trackmybus.model.StudentLoginRequest
import com.aditya.trackmybus.model.StudentLoginResponse
import com.aditya.trackmybus.model.StudentRegisterRequest
import com.aditya.trackmybus.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {

    suspend fun studentRegister(
        request: StudentRegisterRequest
    ): Response<String> =
        RetrofitInstance.api.studentRegister(
            request
        )

    suspend fun studentLogin(
        request: StudentLoginRequest
    ): Response<StudentLoginResponse> =
        RetrofitInstance.api.studentLogin(
            request
        )

    suspend fun driverLogin(
        request: DriverLoginRequest
    ): Response<DriverLoginResponse> =
        RetrofitInstance.api.driverLogin(
            request
        )

    suspend fun driverRegister(
        request: DriverRegisterRequest
    ): Response<String> =
        RetrofitInstance.api.driverRegister(
            request
        )

    suspend fun saveToken(
        request: SaveTokenRequest
    ): Response<String> =
        RetrofitInstance.api.saveToken(
            request
        )

    suspend fun getNotifications(
        studentId: Long
    ): Response<List<NotificationResponse>> =
        RetrofitInstance.api.getNotifications(
            studentId
        )
}
