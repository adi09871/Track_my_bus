package com.example.trackmybus.repository

import com.example.trackmybus.model.BusCreateRequest
import com.example.trackmybus.model.LocationUpdateRequest
import com.example.trackmybus.network.RetrofitInstance

class BusRepository {

    suspend fun getBusesByDriverId(
        driverId: Long
    ) =
        RetrofitInstance.api.getBusesByDriverId(
            driverId
        )

    suspend fun startTrip(
        busId: Long
    ) =
        RetrofitInstance.api.startTrip(
            busId
        )

    suspend fun stopTrip(
        busId: Long
    ) =
        RetrofitInstance.api.stopTrip(
            busId
        )

    suspend fun increaseOccupancy(
        busId: Long
    ) =
        RetrofitInstance.api.increaseOccupancy(
            busId
        )

    suspend fun decreaseOccupancy(
        busId: Long
    ) =
        RetrofitInstance.api.decreaseOccupancy(
            busId
        )

    suspend fun createBus(
        request: BusCreateRequest
    ) =
        RetrofitInstance.api.createBus(
            request
        )

    suspend fun getStopsByBusId(
        busId: Long
    ) =
        RetrofitInstance.api.getStopsByBusId(
            busId
        )

    suspend fun updateLocation(
        request: LocationUpdateRequest
    ) =
        RetrofitInstance.api.updateLocation(
            request
        )

    suspend fun getAllBuses() =
        RetrofitInstance.api.getAllBuses()

    suspend fun getBusById(busId: Long) =
        RetrofitInstance.api.getBusById(busId)
}