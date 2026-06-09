package com.example.trackmybus.network

import com.example.trackmybus.model.DriverLoginRequest
import com.example.trackmybus.model.DriverRegisterRequest
import com.example.trackmybus.model.StudentLoginRequest
import com.example.trackmybus.model.StudentRegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("students/register")
    suspend fun studentRegister(
        @Body request: StudentRegisterRequest

    ): Response<String>

    @POST("students/login")
    suspend fun studentLogin(
        @Body request: StudentLoginRequest
    ): Response<String>

    @POST("drivers/login")
    suspend fun driverLogin(
        @Body request: DriverLoginRequest
    ): Response<String>

    @POST("drivers/register")
    suspend fun driverRegister(
        @Body request: DriverRegisterRequest
    ): Response<String>
}