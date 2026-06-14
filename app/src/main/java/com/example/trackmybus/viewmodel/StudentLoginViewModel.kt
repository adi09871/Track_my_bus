package com.example.trackmybus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.StudentLoginRequest
import kotlinx.coroutines.launch

class StudentLoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.studentLogin(
                        StudentLoginRequest(
                            email = email,
                            password = password
                        )
                    )

                if (response.isSuccessful) {

                    onResult(
                        true,
                        response.body() ?: "Login Successful!"
                    )

                } else {

                    onResult(
                        false,
                        "Login Failed"
                    )
                }

            } catch (e: Exception) {

                onResult(
                    false,
                    e.message ?: "Something went wrong"
                )
            }
        }
    }
}