package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.DriverLoginRequest
import com.aditya.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class DriverLoginViewModel : ViewModel() {

    private val repository = AuthRepository()
    
    var isLoading by mutableStateOf(false)
        private set

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String, Long) -> Unit
    ) {
        if (isLoading) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                val response =
                    repository.driverLogin(
                        DriverLoginRequest(
                            email = email,
                            password = password
                        )
                    )

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val idFromBackend = loginResponse?.driverId ?: -1L
                    
                    if (idFromBackend != -1L) {
                        SessionManager.driverId = idFromBackend
                    }

                    onResult(
                        true,
                        loginResponse?.message ?: "",
                        idFromBackend
                    )

                } else {
                    onResult(
                        false,
                        "Login Failed",
                        -1L
                    )
                }

            } catch (e: Exception) {
                onResult(
                    false,
                    e.message ?: "Unknown Error",
                    -1L
                )
            } finally {
                isLoading = false
            }
        }
    }
}
