package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.Bus
import com.example.trackmybus.model.BusCreateRequest
import com.example.trackmybus.repository.BusRepository
import com.example.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class AddBusViewModel : ViewModel() {

    private val repository = BusRepository()

    var busList by mutableStateOf<List<Bus>>(emptyList())
        private set

    fun loadBuses() {
        viewModelScope.launch {
            try {
                val response = repository.getAllBuses()
                if (response.isSuccessful) {
                    busList = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                // Error loading buses
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
