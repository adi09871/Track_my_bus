package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.Bus
import com.aditya.trackmybus.model.StudentProfileResponse
import com.aditya.trackmybus.repository.ProfileRepository
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

    private var loadedStudentId: Long = -1

    fun fetchProfile(studentId: Long) {
        if (loadedStudentId != studentId) {
            clearState()
        }

        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.getStudentProfile(studentId)
                if (response.isSuccessful) {
                    profileData = response.body()
                    loadedStudentId = studentId
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

    private fun clearState() {
        profileData = null
        assignedBus = null
        error = null
        loadedStudentId = -1
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
