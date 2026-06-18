package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.Bus
import com.example.trackmybus.model.StudentProfileResponse
import com.example.trackmybus.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()

    var profileData by mutableStateOf<StudentProfileResponse?>(null)
        private set

    var assignedBus by mutableStateOf<Bus?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun fetchProfile(studentId: Long) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.getStudentProfile(studentId)
                if (response.isSuccessful) {
                    profileData = response.body()
                    profileData?.busId?.let { busId ->
                        fetchBusDetails(busId)
                    }
                } else {
                    error = "Failed to load profile"
                }
            } catch (e: Exception) {
                error = e.message ?: "An unexpected error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun fetchBusDetails(busId: Long) {
        try {
            val response = repository.getBusById(busId)
            if (response.isSuccessful) {
                assignedBus = response.body()
            }
        } catch (e: Exception) {
            // Bus details failed to load, assignedBus remains null
        }
    }
}
