package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.Bus
import com.aditya.trackmybus.model.BusCreateRequest
import com.aditya.trackmybus.repository.BusRepository
import com.aditya.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class AddBusViewModel : ViewModel() {

    private val repository = BusRepository()

    var busList by mutableStateOf<List<Bus>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadBuses()
    }

    fun loadBuses() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.getAllBuses()
                if (response.isSuccessful) {
                    busList = response.body() ?: emptyList()
                } else {
                    error = "Failed to load buses: ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Error: ${e.message ?: "Unknown error occurred"}"
            } finally {
                isLoading = false
            }
        }
    }

    fun createBus(
        busNumber: String,
        seatCapacity: String,
        routeName: String,
        onSuccess: (Long, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.createBus(
                    BusCreateRequest(
                        busNumber = busNumber,
                        seatCapacity = seatCapacity.toIntOrNull() ?: 0,
                        routeName = routeName,
                        driverId = SessionManager.driverId
                    )
                )

                if (response.isSuccessful) {
                    val busResponse = response.body()
                    onSuccess(
                        busResponse?.busId ?: -1L,
                        busResponse?.message ?: "Bus Created Successfully"
                    )
                }
            } catch (e: Exception) {
                // Error creating bus
            }
        }
    }
}
