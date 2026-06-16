package com.example.trackmybus.userinterface

import android.content.Context
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun OpenStreetMapView(
    context: Context
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

                controller.setCenter(
                    GeoPoint(
                        28.6139,
                        77.2090
                    )
                )

                controller.setZoom(15.0)

                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
            }
        }
    )

}
