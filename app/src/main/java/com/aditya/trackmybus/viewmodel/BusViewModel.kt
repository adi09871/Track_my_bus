package com.aditya.trackmybus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.Bus
import com.aditya.trackmybus.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BusViewModel : ViewModel() {

    private val _buses = MutableStateFlow<List<Bus>>(emptyList())
    val buses: StateFlow<List<Bus>> = _buses

    fun loadBuses() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllBuses()
                if (response.isSuccessful) {
                    _buses.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                // Error loading buses
            }
        }
    }
}
