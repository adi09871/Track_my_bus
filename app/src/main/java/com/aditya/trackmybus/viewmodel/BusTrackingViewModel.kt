package com.aditya.trackmybus.viewmodel

import android.util.Log
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
        if (SessionManager.busId == -1L) return
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
        if (SessionManager.busId == -1L) {
            Log.d("MAP_DEBUG", "SKIP_FETCH: busId is -1")
            return
        }
        viewModelScope.launch {
            try {

                Log.d("MAP_DEBUG", "SESSION_BUS_ID=${SessionManager.busId}")

                val response =
                    RetrofitInstance.api.getCurrentLocation(
                        SessionManager.busId
                    )

                Log.d(
                    "MAP_DEBUG",
                    "HTTP_CODE=${response.code()}"
                )

                if (response.isSuccessful) {

                    location.value = response.body()

                    Log.d(
                        "MAP_DEBUG",
                        "LOCATION_ASSIGNED=${location.value}"
                    )
                } else {
                    Log.e("MAP_DEBUG", "FETCH_FAILED: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {

                Log.e(
                    "MAP_DEBUG",
                    "LOCATION_EXCEPTION",
                    e
                )
            }
        }
    }
}
