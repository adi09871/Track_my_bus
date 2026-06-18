package com.aditya.trackmybus.network

import com.aditya.trackmybus.model.Bus
import com.aditya.trackmybus.model.BusCreateRequest
import com.aditya.trackmybus.model.BusCreateResponse
import com.aditya.trackmybus.model.DriverLoginRequest
import com.aditya.trackmybus.model.DriverLoginResponse
import com.aditya.trackmybus.model.DriverProfileResponse
import com.aditya.trackmybus.model.DriverRegisterRequest
import com.aditya.trackmybus.model.LocationUpdateRequest
import com.aditya.trackmybus.model.NotificationResponse
import com.aditya.trackmybus.model.SaveStopsRequest
import com.aditya.trackmybus.model.SaveTokenRequest
import com.aditya.trackmybus.model.Stop
import com.aditya.trackmybus.model.StudentLoginRequest
import com.aditya.trackmybus.model.StudentLoginResponse
import com.aditya.trackmybus.model.StudentProfileResponse
import com.aditya.trackmybus.model.StudentRegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST("students/register")
    suspend fun studentRegister(
        @Body request: StudentRegisterRequest

    ): Response<String>

    @POST("students/login")
    suspend fun studentLogin(
        @Body request: StudentLoginRequest
    ): Response<StudentLoginResponse>

    @GET("students/{studentId}")
    suspend fun getStudentProfile(
        @Path("studentId") studentId: Long
    ): Response<StudentProfileResponse>

    @POST("drivers/login")
    suspend fun driverLogin(
        @Body request: DriverLoginRequest
    ): Response<DriverLoginResponse>

    @POST("drivers/register")
    suspend fun driverRegister(
        @Body request: DriverRegisterRequest
    ): Response<String>

    @GET("drivers/{driverId}")
    suspend fun getDriverById(
        @Path("driverId") driverId: Long
    ): Response<DriverProfileResponse>

    @POST("buses/create")
    suspend fun createBus(
        @Body request: BusCreateRequest
    ): Response<BusCreateResponse>

    @GET("buses/driver/{driverId}")
    suspend fun getBusesByDriverId(
        @Path("driverId") driverId: Long
    ): Response<List<Bus>>

    @POST("stops/save")
    suspend fun saveStops(
        @Body request: SaveStopsRequest
    ): Response<String>

    @POST("location/update")
    suspend fun updateLocation(

        @Body request: LocationUpdateRequest

    ): Response<String>

    @GET("stops/bus/{busId}")
    suspend fun getStopsByBusId(
        @Path("busId") busId: Long
    ): Response<List<Stop>>

    @POST("buses/increase/{busId}")
    suspend fun increaseOccupancy(
        @Path("busId") busId: Long
    ): Response<Bus>

    @POST("buses/decrease/{busId}")
    suspend fun decreaseOccupancy(
        @Path("busId") busId: Long
    ): Response<Bus>

    @POST("buses/start-trip/{busId}")
    suspend fun startTrip(
        @Path("busId") busId: Long
    ): Response<Bus>

    @POST("buses/stop-trip/{busId}")
    suspend fun stopTrip(
        @Path("busId") busId: Long
    ): Response<Bus>

    @GET("buses/{busId}")
    suspend fun getBusById(
        @Path("busId") busId: Long
    ): Response<Bus>

    @GET("buses")
    suspend fun getAllBuses(): Response<List<Bus>>

    @GET("location/{busId}")
    suspend fun getCurrentLocation(
        @Path("busId") busId: Long
    ): Response<com.aditya.trackmybus.model.Location>

    @POST("students/save-token")
    suspend fun saveToken(
        @Body request: SaveTokenRequest
    ): Response<String>


    @GET("students/notifications/{studentId}")
    suspend fun getNotifications(
        @Path("studentId") studentId: Long
    ): Response<List<NotificationResponse>>

}