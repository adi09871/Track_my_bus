package com.aditya.trackmybus.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.DriverLoginRequest
import com.aditya.trackmybus.session.SessionManager
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
                    val driverId = loginResponse?.driverId ?: -1L
                    
                    if (driverId != -1L) {
                        SessionManager.driverId = driverId
                        Log.d("LOGIN_FLOW", "LOGIN_SUCCESS: driverId = $driverId")
                        Log.d("LOGIN_FLOW", "DRIVER_ID_SAVED: SessionManager.driverId = ${SessionManager.driverId}")
                    }

                    onResult(
                        true,
                        loginResponse?.message ?: "",
                        driverId
                    )

                } else {
                    Log.e("LOGIN_FLOW", "Login Failed: ${response.message()}")
                    onResult(
                        false,
                        "Login Failed",
                        -1
                    )
                }

            } catch (e: Exception) {
                Log.e("LOGIN_FLOW", "Login Error", e)
                onResult(
                    false,
                    e.message ?: "Unknown Error",
                    -1
                )
            }
        }
    }
}
