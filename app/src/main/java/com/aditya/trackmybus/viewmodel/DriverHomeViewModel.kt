package com.aditya.trackmybus.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.Bus
import com.aditya.trackmybus.repository.BusRepository
import com.aditya.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class DriverHomeViewModel : ViewModel() {

    private val repository = BusRepository()

    var bus by mutableStateOf<Bus?>(null)
        private set
    var stopsCount by mutableStateOf(0)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var isTripOperationLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun loadBus() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.getBusById(SessionManager.busId)

                if (response.isSuccessful) {
                    bus = response.body()
                } else {
                    error = "Failed to load bus"
                }

                val stopsResponse = repository.getStopsByBusId(SessionManager.busId)
                if (stopsResponse.isSuccessful) {
                    stopsCount = stopsResponse.body()?.size ?: 0
                }

            } catch (e: Exception) {
                error = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }

    fun startTrip(onResult: (Boolean) -> Unit) {
        Log.d("TRIP_DEBUG", "VIEWMODEL_START_TRIP_CALLED")
        viewModelScope.launch {
            isTripOperationLoading = true
            try {
                Log.d("TRIP_DEBUG", "CHECK_BUS_OBJECT: bus=${bus?.busNumber ?: "null"}")
                bus?.let {
                    val busId = it.id
                    Log.d("TRIP_DEBUG", "API_START_TRIP_REQUEST: busId=$busId")
                    val response = repository.startTrip(busId)

                    Log.d("TRIP_DEBUG", "HTTP_CODE=${response.code()}")
                    Log.d("TRIP_DEBUG", "HTTP_MESSAGE=${response.message()}")

                    try {
                        Log.d(
                            "TRIP_DEBUG",
                            "ERROR_BODY=${response.errorBody()?.string()}"
                        )
                    } catch (e: Exception) {
                        Log.e(
                            "TRIP_DEBUG",
                            "ERROR_BODY_READ_FAILED",
                            e
                        )
                    }

                    Log.d(
                        "TRIP_DEBUG",
                        "API_START_TRIP_RESPONSE: isSuccessful=${response.isSuccessful}"
                    )

                    if (response.isSuccessful) {
                        bus = response.body()
                        Log.d("TRIP_DEBUG", "TRIP_STATE_UPDATED: isTripActive=${bus?.isTripActive}")
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                } ?: run {
                    Log.e("TRIP_DEBUG", "START_TRIP_FAILED: bus is null")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("TRIP_DEBUG", "START_TRIP_EXCEPTION", e)
                onResult(false)
            } finally {
                isTripOperationLoading = false
            }
        }
    }

    fun stopTrip(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            isTripOperationLoading = true
            try {
                bus?.let {
                    val response = repository.stopTrip(it.id)
                    if (response.isSuccessful) {
                        bus = response.body()
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                } ?: onResult(false)
            } catch (e: Exception) {
                onResult(false)
            } finally {
                isTripOperationLoading = false
            }
        }
    }

    fun increaseOccupancy() {
        if (isTripOperationLoading) return
        viewModelScope.launch {
            try {
                bus?.let {
                    if (it.currentOccupancy < it.seatCapacity) {
                        val response = repository.increaseOccupancy(it.id)
                        if (response.isSuccessful) {
                            val updatedBus = response.body()
                            // Preserve current trip state if backend returns inconsistent data
                            bus = updatedBus?.copy(isTripActive = bus?.isTripActive ?: updatedBus.isTripActive)
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun decreaseOccupancy() {
        if (isTripOperationLoading) return
        viewModelScope.launch {
            try {
                bus?.let {
                    if (it.currentOccupancy > 0) {
                        val response = repository.decreaseOccupancy(it.id)
                        if (response.isSuccessful) {
                            val updatedBus = response.body()
                            // Preserve current trip state if backend returns inconsistent data
                            bus = updatedBus?.copy(isTripActive = bus?.isTripActive ?: updatedBus.isTripActive)
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
