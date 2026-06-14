package com.example.trackmybus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.DriverRegisterRequest
import kotlinx.coroutines.launch

class DriverSignupViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun signup(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.driverRegister(
                        DriverRegisterRequest(
                            name = name,
                            email = email,
                            password = password
                        )
                    )

                if (response.isSuccessful) {

                    onResult(
                        true,
                        response.body() ?: ""
                    )

                } else {

                    onResult(
                        false,
                        "Registration Failed"
                    )
                }

            } catch (e: Exception) {

                onResult(
                    false,
                    e.message ?: "Unknown Error"
                )
            }
        }
    }
}

