package com.example.trackmybus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.Bus
import com.example.trackmybus.network.RetrofitInstance
import com.example.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class StudentHomeViewModel : ViewModel() {

    var bus = mutableStateOf<Bus?>(null)
        private set
    var totalStops = mutableStateOf(0)
        private set

    fun loadBus() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getBusById(SessionManager.busId)
                if (response.isSuccessful) {
                    bus.value = response.body()
                }
            } catch (e: Exception) {
                // Handle error
            }

            try {
                val stopsResponse = RetrofitInstance.api.getStopsByBusId(SessionManager.busId)
                if (stopsResponse.isSuccessful) {
                    totalStops.value = stopsResponse.body()?.size ?: 0
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
