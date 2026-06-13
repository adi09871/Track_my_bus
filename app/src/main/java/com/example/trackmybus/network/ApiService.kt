package com.example.trackmybus.network

import com.example.trackmybus.model.Bus
import com.example.trackmybus.model.BusCreateRequest
import com.example.trackmybus.model.BusCreateResponse
import com.example.trackmybus.model.DriverLoginRequest
import com.example.trackmybus.model.DriverLoginResponse
import com.example.trackmybus.model.DriverRegisterRequest
import com.example.trackmybus.model.LocationUpdateRequest
import com.example.trackmybus.model.SaveStopsRequest
import com.example.trackmybus.model.Stop
import com.example.trackmybus.model.StudentLoginRequest
import com.example.trackmybus.model.StudentRegisterRequest
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
    ): Response<String>

    @POST("drivers/login")
    suspend fun driverLogin(
        @Body request: DriverLoginRequest
    ): Response<DriverLoginResponse>

    @POST("drivers/register")
    suspend fun driverRegister(
        @Body request: DriverRegisterRequest
    ): Response<String>

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


}