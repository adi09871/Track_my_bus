package com.example.trackmybus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.StudentRegisterRequest
import kotlinx.coroutines.launch

class StudentSignupViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun signup(
        name: String,
        email: String,
        password: String,
        collegeId: String,
        busId: Long,
        onResult: (Boolean, String) -> Unit
    ) {

        viewModelScope.launch {

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

