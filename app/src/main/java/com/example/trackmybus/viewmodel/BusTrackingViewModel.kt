package com.example.trackmybus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.Location
import com.example.trackmybus.network.RetrofitInstance
import com.example.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class BusTrackingViewModel : ViewModel() {

    var location = mutableStateOf<Location?>(null)
        private set

    fun loadBusLocation() {

        viewModelScope.launch {

            try {
                println("STUDENT BUS ID = ${SessionManager.busId}")
                val response =
                    RetrofitInstance.api.getCurrentLocation(
                        SessionManager.busId
                    )

                println("LOCATION RESPONSE = ${response.body()}")
                if (response.isSuccessful) {

                    location.value =
                        response.body() as Location?

                    println(
                        "BUS LOCATION = ${response.body()}"
                    )
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}