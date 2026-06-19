package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.Bus
import com.aditya.trackmybus.network.RetrofitInstance
import com.aditya.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class StudentHomeViewModel : ViewModel() {

    var bus = mutableStateOf<Bus?>(null)
        private set
    var totalStops = mutableStateOf(0)
        private set
    var isLoading = mutableStateOf(false)
        private set
    var error = mutableStateOf<String?>(null)
        private set

    init {
        loadBus()
    }

    fun loadBus() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                val response = RetrofitInstance.api.getBusById(SessionManager.busId)
                if (response.isSuccessful) {
                    bus.value = response.body()
                } else {
                    error.value = "Failed to load bus details"
                }

                val stopsResponse = RetrofitInstance.api.getStopsByBusId(SessionManager.busId)
                if (stopsResponse.isSuccessful) {
                    totalStops.value = stopsResponse.body()?.size ?: 0
                }
            } catch (e: Exception) {
                error.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }
}
