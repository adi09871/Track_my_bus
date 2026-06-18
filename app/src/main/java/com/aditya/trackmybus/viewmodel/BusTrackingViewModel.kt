package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.Location
import com.aditya.trackmybus.network.RetrofitInstance
import com.aditya.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class BusTrackingViewModel : ViewModel() {

    var location = mutableStateOf<Location?>(null)
        private set

    var busNumber = mutableStateOf<String?>(null)
        private set

    fun loadBusDetails() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getBusById(SessionManager.busId)
                if (response.isSuccessful) {
                    busNumber.value = response.body()?.busNumber
                }
            } catch (_: Exception) {
            }
        }
    }

    fun loadBusLocation() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentLocation(SessionManager.busId)
                if (response.isSuccessful) {
                    location.value = response.body()
                }
            } catch (_: Exception) {
                // Handle error
            }
        }
    }
}
