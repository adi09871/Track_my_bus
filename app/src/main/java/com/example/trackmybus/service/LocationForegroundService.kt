package com.example.trackmybus.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.trackmybus.R
import com.example.trackmybus.model.LocationUpdateRequest
import com.example.trackmybus.repository.BusRepository
import com.example.trackmybus.session.SessionManager
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LocationForegroundService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val repository = BusRepository()

    private val serviceScope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO
        )

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        startForeground(
            1,
            createNotification()
        )

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        locationCallback =
            object : LocationCallback() {

                override fun onLocationResult(
                    locationResult: LocationResult
                ) {

                    locationResult.lastLocation?.let { location ->

                        updateBusLocation(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
            }

        startLocationUpdates()
    }

    private fun updateBusLocation(
        latitude: Double,
        longitude: Double
    ) {

        val busId = SessionManager.busId

        if (busId == -1L) {
            return
        }

        serviceScope.launch {

            try {

                repository.updateLocation(
                    LocationUpdateRequest(
                        busId = busId,
                        latitude = latitude,
                        longitude = longitude
                    )
                )

            } catch (_: Exception) {
                // Location update failed
            }
        }
    }

    private fun startLocationUpdates() {

        val locationRequest =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000L
            )
                .setMinUpdateIntervalMillis(
                    3000L
                )
                .build()

        try {

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

        } catch (_: SecurityException) {
            // Location permission error
        }
    }

    override fun onDestroy() {

        fusedLocationClient.removeLocationUpdates(
            locationCallback
        )

        serviceScope.coroutineContext.cancel()

        super.onDestroy()
    }

    override fun onBind(
        intent: Intent?
    ): IBinder? {
        return null
    }

    private fun createNotification(): Notification {

        return NotificationCompat.Builder(
            this,
            "location_channel"
        )
            .setContentTitle(
                "Bus Tracking Active"
            )
            .setContentText(
                "Live location is being shared"
            )
            .setSmallIcon(
                R.mipmap.ic_launcher
            )
            .setPriority(
                NotificationCompat.PRIORITY_LOW
            )
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel =
                NotificationChannel(
                    "location_channel",
                    "Location Tracking",
                    NotificationManager.IMPORTANCE_LOW
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                channel
            )
        }
    }
}
