package com.example.trackmybus.userinterface

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.trackmybus.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OpenStreetMapView(
    context: Context,
    latitude: Double,
    longitude: Double,
    recenterTrigger: Int = 0
) {
    var hasCenteredInitially by remember { mutableStateOf(false) }
    var previousRecenterTrigger by remember { mutableStateOf(recenterTrigger) }

    AndroidView(
        factory = {
            Configuration.getInstance().load(
                context,
                context.getSharedPreferences("osm", Context.MODE_PRIVATE)
            )
            Configuration.getInstance().userAgentValue = context.packageName

            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                controller.setZoom(15.0)
            }
        },
        update = { mapView ->
            val busPoint = GeoPoint(latitude, longitude)

            // Center logic
            if (!hasCenteredInitially && latitude != 0.0 && longitude != 0.0) {
                mapView.controller.setCenter(busPoint)
                hasCenteredInitially = true
            } else if (recenterTrigger != previousRecenterTrigger) {
                mapView.controller.animateTo(busPoint)
                mapView.controller.setZoom(17.0) // Zoom in on recenter
                previousRecenterTrigger = recenterTrigger
            }

            // Marker logic
            mapView.overlays.clear()
            val marker = Marker(mapView)
            marker.position = busPoint
            marker.title = "College Bus"

            val drawable = ContextCompat.getDrawable(context, R.drawable.bus_marker)
            if (drawable != null) {
                val bitmap = (drawable as BitmapDrawable).bitmap
                val smallBitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, true)
                marker.icon = smallBitmap.toDrawable(context.resources)
            }

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            mapView.overlays.add(marker)
            mapView.invalidate()
        }
    )
}
