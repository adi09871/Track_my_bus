package com.example.trackmybus.userinterface

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DriverBottomBar(
    selectedScreen: String,
    onBusClick: () -> Unit,
    onTripClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedScreen == "bus",
            onClick = onBusClick,
            icon = { Icon(Icons.Default.DirectionsBus, contentDescription = null) },
            label = { Text("Bus") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF6A39FF),
                selectedTextColor = Color(0xFF6A39FF),
                indicatorColor = Color(0xFFF0EDFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = selectedScreen == "trip",
            onClick = onTripClick,
            icon = { Icon(Icons.Default.NearMe, contentDescription = null) },
            label = { Text("Trip") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF6A39FF),
                selectedTextColor = Color(0xFF6A39FF),
                indicatorColor = Color(0xFFF0EDFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = selectedScreen == "profile",
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF6A39FF),
                selectedTextColor = Color(0xFF6A39FF),
                indicatorColor = Color(0xFFF0EDFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}
