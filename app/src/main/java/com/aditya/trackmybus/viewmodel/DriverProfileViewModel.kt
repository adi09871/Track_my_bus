package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.DriverProfileResponse
import com.aditya.trackmybus.repository.ProfileRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DriverProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()
    private var fetchJob: Job? = null

    var profileData by mutableStateOf<DriverProfileResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun fetchProfile(driverId: Long) {
        profileData = null
        error = null
        isLoading = true

        fetchJob?.cancel()
        
        fetchJob = viewModelScope.launch {
            error = null
            try {
                val response = repository.getDriverById(driverId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        profileData = data
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
}
