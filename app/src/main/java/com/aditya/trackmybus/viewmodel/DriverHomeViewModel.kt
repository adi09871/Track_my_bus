package com.aditya.trackmybus.viewmodel

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
    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadBus()
    }

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

    fun startTrip() {
        viewModelScope.launch {
            try {
                bus?.let {
                    val response = repository.startTrip(it.id)
                    if (response.isSuccessful) {
                        bus = response.body()
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun stopTrip() {
        viewModelScope.launch {
            try {
                bus?.let {
                    val response = repository.stopTrip(it.id)
                    if (response.isSuccessful) {
                        bus = response.body()
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun increaseOccupancy() {
        viewModelScope.launch {
            try {
                bus?.let {
                    if (it.currentOccupancy < it.seatCapacity) {
                        val response = repository.increaseOccupancy(it.id)
                        if (response.isSuccessful) {
                            bus = response.body()
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun decreaseOccupancy() {
        viewModelScope.launch {
            try {
                bus?.let {
                    if (it.currentOccupancy > 0) {
                        val response = repository.decreaseOccupancy(it.id)
                        if (response.isSuccessful) {
                            bus = response.body()
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
