package com.example.trackmybus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmybus.model.NotificationResponse
import kotlinx.coroutines.launch

class AlertsViewModel : ViewModel() {

    private val repository = AuthRepository()

    val notifications =
        mutableStateListOf<NotificationResponse>()

    var isLoading by mutableStateOf(false)
        private set

    fun loadNotifications(
        studentId: Long
    ) {

        viewModelScope.launch {
            isLoading = true
            try {

                val response =
                    repository.getNotifications(
                        studentId
                    )

                if (
                    response.isSuccessful
                ) {

                    notifications.clear()

                    notifications.addAll(
                        response.body()
                            ?: emptyList()
                    )
                }

            } catch (_: Exception) {

            } finally {
                isLoading = false
            }
        }
    }
}
