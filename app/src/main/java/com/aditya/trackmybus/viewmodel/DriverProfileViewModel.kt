package com.aditya.trackmybus.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.DriverProfileResponse
import com.aditya.trackmybus.repository.ProfileRepository
import kotlinx.coroutines.launch

class DriverProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()

    var profileData by mutableStateOf<DriverProfileResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private var loadedDriverId: Long = -1

    fun fetchProfile(driverId: Long) {
        if (loadedDriverId != driverId) {
            Log.d("PROFILE_FLOW", "PREVIOUS_PROFILE_ID: $loadedDriverId")
            Log.d("PROFILE_FLOW", "CURRENT_DRIVER_ID: $driverId")
            clearState()
            Log.d("PROFILE_FLOW", "PROFILE_STATE_CLEARED")
        }

        Log.d("PROFILE_FLOW", "NEW_PROFILE_FETCH_STARTED: $driverId")
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.getDriverById(driverId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        profileData = data
                        loadedDriverId = driverId
                        Log.d("PROFILE_FLOW", "PROFILE_LOAD_SUCCESS for $driverId")
                    } else {
                        error = "No profile data found"
                    }
                } else {
                    error = "Failed to load profile: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Network Error: ${e.localizedMessage ?: "Unknown"}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun clearState() {
        profileData = null
        error = null
        loadedDriverId = -1
    }
}
