package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trackmybus.userinterface.Stop

class AddStopsViewModel : ViewModel() {

    private val availableStopNames = listOf(
        "Campus Gate",
        "Library",
        "Sector 14",
        "Metro Station",
        "Old Town",
        "Riverside",
        "City Center",
        "Market Square",
        "Bus Depot",
        "Dwarka"
    )

    var stops by mutableStateOf(
        availableStopNames.map {
            Stop(it)
        }
    )
        private set

    fun toggleStopSelection(
        stopName: String
    ) {

        stops = stops.map {

            if (it.name == stopName) {

                it.copy(
                    isSelected = !it.isSelected
                )

            } else {

                it
            }
        }
    }
}