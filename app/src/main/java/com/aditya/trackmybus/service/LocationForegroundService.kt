package com.aditya.trackmybus.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.aditya.trackmybus.R
import com.aditya.trackmybus.model.LocationUpdateRequest
import com.aditya.trackmybus.repository.BusRepository
import com.aditya.trackmybus.session.SessionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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
        Log.d("TRIP_DEBUG", "LOCATION_SERVICE_CREATED")

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
                    Log.d("TRIP_DEBUG", "LOCATION_CALLBACK_TRIGGERED")
                    locationResult.lastLocation?.let { location ->

                        updateBusLocation(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
            }
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.d("TRIP_DEBUG", "LOCATION_SERVICE_ON_START_COMMAND: startId=$startId")
        
        // Ensure tracking is active when service is started/restarted
        startLocationUpdates()

        return START_STICKY
    }

    private fun updateBusLocation(
        latitude: Double,
        longitude: Double
    ) {

        val busId = SessionManager.busId

        if (busId == -1L) {
            return
        }
        
        Log.d("TRIP_DEBUG", "LOCATION_SENT_TO_API: lat=$latitude, lon=$longitude")

        serviceScope.launch {

            try {

                val response = repository.updateLocation(
                    LocationUpdateRequest(
                        busId = busId,
                        latitude = latitude,
                        longitude = longitude
                    )
                )
                
                if (response.isSuccessful) {
                    Log.d("TRIP_DEBUG", "LOCATION_UPLOAD_SUCCESS")
                } else {
                    Log.e("TRIP_DEBUG", "LOCATION_UPLOAD_FAILED: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("TRIP_DEBUG", "LOCATION_UPLOAD_EXCEPTION", e)
            }
        }
    }

    private fun startLocationUpdates() {
        Log.d("TRIP_DEBUG", "REQUEST_LOCATION_UPDATES_START")
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
            Log.d("TRIP_DEBUG", "REQUEST_LOCATION_UPDATES_SUCCESS")

        } catch (e: SecurityException) {
            Log.e("TRIP_DEBUG", "REQUEST_LOCATION_UPDATES_SECURITY_EXCEPTION", e)
        } catch (e: Exception) {
            Log.e("TRIP_DEBUG", "REQUEST_LOCATION_UPDATES_EXCEPTION", e)
        }
    }

    override fun onDestroy() {
        Log.d("TRIP_DEBUG", "LOCATION_SERVICE_DESTROY_START")
        
        Log.d("TRIP_DEBUG", "REMOVE_LOCATION_UPDATES")
        if (::fusedLocationClient.isInitialized && ::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }

        serviceScope.coroutineContext.cancel()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }

        Log.d("TRIP_DEBUG", "LOCATION_SERVICE_DESTROYED_COMPLETE")

        super.onDestroy()
    }

    override fun onBind(
        intent: Intent?
    ): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        val logoBitmap = android.graphics.BitmapFactory.decodeResource(resources, R.drawable.logo)
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
                R.drawable.logo
            )
            .setLargeIcon(logoBitmap)
            .setColor(ContextCompat.getColor(this, R.color.brand_purple))
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