package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.StudentProfileResponse
import com.example.trackmybus.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()

    var profileData by mutableStateOf<StudentProfileResponse?>(null)
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
}
