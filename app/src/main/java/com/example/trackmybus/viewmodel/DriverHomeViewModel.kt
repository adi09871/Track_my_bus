package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.Bus
import com.example.trackmybus.repository.BusRepository
import com.example.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class DriverHomeViewModel : ViewModel(){

    private val repository = BusRepository()

    var bus by mutableStateOf<Bus?>(null)
        private set
    var stopsCount by mutableStateOf(0)
        private set
    fun loadBus() {
        println("DEBUG: loadBus() triggered")
        println("DEBUG: driverId = ${SessionManager.driverId}")
        println("DEBUG: busId in Session = ${SessionManager.busId}")

        viewModelScope.launch {
            try {
                // Fetch the specific bus directly by its ID
                val response = repository.getBusById(SessionManager.busId)

                if (response.isSuccessful) {
                    bus = response.body()
                    println("DEBUG: API Call Successful. Bus loaded: $bus")
                } else {
                    println("DEBUG: API Call Failed. Code: ${response.code()}, Error: ${response.errorBody()?.string()}")
                }

                val stopsResponse = repository.getStopsByBusId(SessionManager.busId)
                if (stopsResponse.isSuccessful) {
                    stopsCount = stopsResponse.body()?.size ?: 0
                    println("DEBUG: STOPS COUNT = $stopsCount")
                }

            } catch (e: Exception) {
                println("DEBUG: Exception in loadBus(): ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun startTrip() {

        viewModelScope.launch {

            try {

                bus?.let {

                    val response =
                        repository.startTrip(
                            it.id
                        )

                    if (response.isSuccessful) {

                        bus = response.body()
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun stopTrip() {

        viewModelScope.launch {

            try {

                bus?.let {

                    val response =
                        repository.stopTrip(
                            it.id
                        )

                    if (response.isSuccessful) {

                        bus = response.body()
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
    fun increaseOccupancy() {

        viewModelScope.launch {

            try {

                bus?.let {

                    if (it.currentOccupancy < it.seatCapacity) {

                        val response =
                            repository.increaseOccupancy(
                                it.id
                            )

                        if (response.isSuccessful) {

                            bus = response.body()
                        }
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
    fun decreaseOccupancy() {

        viewModelScope.launch {

            try {

                bus?.let {

                    if (it.currentOccupancy > 0) {

                        val response =
                            repository.decreaseOccupancy(
                                it.id
                            )

                        if (response.isSuccessful) {

                            bus = response.body()
                        }
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}
