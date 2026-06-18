package com.example.trackmybus.userinterface

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackmybus.viewmodel.BusTrackingViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusTrackingScreen(
    onHomeClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    var recenterTrigger by remember { mutableStateOf(0) }
    val viewModel: BusTrackingViewModel = viewModel()

    val location by viewModel.location
    val busNumber by viewModel.busNumber

    LaunchedEffect(Unit) {
        viewModel.loadBusDetails()
        while (true) {
            viewModel.loadBusLocation()
            delay(5000.milliseconds)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (busNumber != null) "Bus $busNumber" else "Bus",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Live Tracking",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    label = { Text("Track") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.tertiary
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onAlertsClick,
                    icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                    label = { Text("Alerts") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { recenterTrigger++ },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "Center Map")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            OpenStreetMapView(
                context = context,
                latitude = location?.latitude ?: 0.0,
                longitude = location?.longitude ?: 0.0,
                recenterTrigger = recenterTrigger
            )

            // Bottom Info Card
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .animateContentSize()
                    .clickable { isExpanded = !isExpanded },
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .fillMaxWidth()
                ) {
                    // Pull bar
                    Box(
                        modifier = Modifier
                            .size(width = 40.dp, height = 4.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(2.dp))
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier.size(44.dp),
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.DirectionsBus, null, tint = Color.White, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "In transit",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "On route to College",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        Surface(
                            color = Color(0xFFE8F5E9), // Keeping green for live status
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(modifier = Modifier.size(6.dp), color = Color(0xFF4CAF50), shape = CircleShape) {}
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Live", color = Color(0xFF4CAF50), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                TrackingInfoItem(Modifier.weight(1f), Icons.Default.Route, "Distance", "1.2 km")
                                TrackingInfoItem(Modifier.weight(1f), Icons.Default.Group, "Occupancy", "25/40")
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Update, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Updated 30s ago", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text("Driver: R. Mehta", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackingInfoItem(modifier: Modifier, icon: ImageVector, label: String, value: String) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = MaterialTheme.colorScheme.onSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusTrackingScreenPreview() {
    BusTrackingScreen(
        onHomeClick = {},
        onAlertsClick = {},
        onProfileClick = {},
        onBackClick = {}
    )
}
