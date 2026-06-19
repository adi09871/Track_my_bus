package com.aditya.trackmybus.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.trackmybus.model.StudentLoginRequest
import com.aditya.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

class StudentLoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun login(
        email: String,
        password: String,
        onResult: (
            Boolean,
            String,
            Long,
            String,
            Long
        ) -> Unit
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

                    val student =
                        response.body()

                    if (student != null) {
                        SessionManager.studentId = student.id
                        SessionManager.studentName = student.name
                        SessionManager.busId = student.busId ?: -1L
                        
                        Log.d("LOGIN_FLOW", "STUDENT_LOGIN_SUCCESS: studentId = ${student.id}")

                        onResult(
                            true,
                            "Login Successful!",
                            student.id,
                            student.name,
                            student.busId ?: -1
                        )

                    } else {

                        onResult(
                            false,
                            "Login Failed",
                            -1,
                            "",
                            -1
                        )
                    }

                } else {

                    onResult(
                        false,
                        "Login Failed",
                        -1,
                        "",
                        -1
                    )
                }

            } catch (e: Exception) {

                onResult(
                    false,
                    e.message ?: "Something went wrong",
                    -1,
                    "",
                    -1
                )
            }
        }
    }
}
