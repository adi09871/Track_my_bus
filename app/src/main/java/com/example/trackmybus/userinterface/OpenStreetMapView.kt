package com.example.trackmybus.userinterface

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.trackmybus.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.core.graphics.drawable.toDrawable

@Composable
fun OpenStreetMapView(
    context: Context,
    latitude: Double,
    longitude: Double
) {

    AndroidView(

        factory = {

            Configuration.getInstance().load(
                context,
                context.getSharedPreferences(
                    "osm",
                    Context.MODE_PRIVATE
                )
            )

            Configuration.getInstance().userAgentValue =
                context.packageName

            MapView(context).apply {

                setTileSource(
                    TileSourceFactory.MAPNIK
                )

                setMultiTouchControls(true)

                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
            }
        },

        update = { mapView ->

            val busPoint = GeoPoint(
                latitude,
                longitude
            )

            mapView.controller.setCenter(
                busPoint
            )

            mapView.controller.setZoom(
                15.0
            )

            mapView.overlays.clear()

            val marker = Marker(mapView)

            marker.position = busPoint

            marker.title = "College Bus"

            val drawable =
                ContextCompat.getDrawable(
                    context,
                    R.drawable.bus_marker
                )

            val bitmap =
                (drawable as BitmapDrawable).bitmap

            val smallBitmap =
                Bitmap.createScaledBitmap(
                    bitmap,
                    48,
                    48,
                    true
                )

            marker.icon =
                smallBitmap.toDrawable(
                    context.resources
                )

            marker.setAnchor(
                Marker.ANCHOR_CENTER,
                Marker.ANCHOR_CENTER
            )

            mapView.overlays.add(
                marker
            )

            mapView.invalidate()
        }
    )
}
