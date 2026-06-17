package com.example.trackmybus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.DriverLoginRequest
import kotlinx.coroutines.launch

class DriverLoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String, Long) -> Unit
    ) {

        viewModelScope.launch {

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

                    onResult(
                        true,
                        loginResponse?.message ?: "",
                        loginResponse?.driverId ?: -1
                    )

                } else {

                    onResult(
                        false,
                        "Login Failed",
                        -1
                    )
                }

            } catch (e: Exception) {

                onResult(
                    false,
                    e.message ?: "Unknown Error",
                    -1
                )
            }
        }
    }
}
