package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.DriverRegisterRequest
import kotlinx.coroutines.launch

class DriverSignupViewModel : ViewModel() {

    private val repository = AuthRepository()

    var isLoading by mutableStateOf(false)
        private set

    fun signup(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.driverRegister(
                    DriverRegisterRequest(
                        name = name,
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    onResult(true, response.body() ?: "Registration Successful")
                } else {
                    onResult(false, "Registration Failed")
                }
            } catch (e: Exception) {
                onResult(false, e.message ?: "Unknown Error")
            } finally {
                isLoading = false
            }
        }
    }
}
