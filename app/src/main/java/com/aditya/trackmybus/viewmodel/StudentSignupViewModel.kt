package com.aditya.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.StudentRegisterRequest
import kotlinx.coroutines.launch

class StudentSignupViewModel : ViewModel() {

    private val repository = AuthRepository()

    var isLoading by mutableStateOf(false)
        private set

    fun signup(
        name: String,
        email: String,
        password: String,
        collegeId: String,
        busId: Long,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response =
                    repository.studentRegister(
                        StudentRegisterRequest(
                            name = name,
                            email = email,
                            password = password,
                            collegeId = collegeId,
                            busId = busId
                        )
                    )

                if (response.isSuccessful) {
                    onResult(
                        true,
                        response.body() ?: "Student Registered Successfully"
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
            } finally {
                isLoading = false
            }
        }
    }
}
