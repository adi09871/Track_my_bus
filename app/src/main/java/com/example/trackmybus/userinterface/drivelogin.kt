package com.example.trackmybus.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DriverLogin(
    onBackClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var onboardCount by remember { mutableIntStateOf(18) }
    val maxCapacity = 40
    var isTripStarted by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.DirectionsBus, contentDescription = null) },
                    label = { Text("Bus") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0061D5),
                        selectedTextColor = Color(0xFF0061D5),
                        indicatorColor = Color(0xFFE3F2FD)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.NearMe, contentDescription = null) },
                    label = { Text("Trip") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FBFF))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "B-204",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Bus management",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Bus Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            color = Color(0xFF0061D5),
                            shape = CircleShape
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.DirectionsBus, null, tint = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Bus B-204", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "Campus → Sector 14", color = Color.Gray, fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BusDetailItem(Icons.Default.Route, "Route", "12")
                        BusDetailItem(Icons.Default.Groups, "Seats", "40")
                        BusDetailItem(Icons.Default.Schedule, "Shift", "AM")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Current Occupancy Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Current occupancy", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "Update as students board", color = Color.Gray, fontSize = 13.sp)
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = "$onboardCount", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(text = "/$maxCapacity", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = { onboardCount.toFloat() / maxCapacity },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color(0xFF0061D5),
                        trackColor = Color(0xFFE3F2FD),
                        strokeCap = StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { if (onboardCount > 0) onboardCount-- },
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFFF0F7FF), CircleShape)
                        ) {
                            Icon(Icons.Default.Remove, null, tint = Color.Black, modifier = Modifier.size(32.dp))
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$onboardCount", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                            Text(text = "ONBOARD", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                        }

                        IconButton(
                            onClick = { if (onboardCount < maxCapacity) onboardCount++ },
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFF0061D5), CircleShape)
                        ) {
                            Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(32.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Trip Controls Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Trip controls", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Surface(
                            color = Color(0xFFF0F4F8),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(modifier = Modifier.size(6.dp), color = Color.Gray, shape = CircleShape) {}
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isTripStarted) "Trip started" else "Trip not started",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { isTripStarted = true },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0061D5)),
                            enabled = !isTripStarted
                        ) {
                            Icon(Icons.Default.PlayArrow, null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Start trip", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { isTripStarted = false },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB2B2)),
                            enabled = isTripStarted
                        ) {
                            Icon(Icons.Default.Stop, null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Stop trip", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Items
            StatusItem(Icons.Default.GpsFixed, "GPS status", "Active", Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(12.dp))
            StatusItem(Icons.Default.Timeline, "Trip status", "Idle", Color.Gray)

            Spacer(modifier = Modifier.height(20.dp))
            
            TextButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Logout", color = Color(0xFF0061D5))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DriverLoginPreview() {
    DriverLogin(onBackClick = {}, onLoginSuccess = {})
}
