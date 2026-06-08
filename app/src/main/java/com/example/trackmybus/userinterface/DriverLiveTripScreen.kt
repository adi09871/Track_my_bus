package com.example.trackmybus.userinterface

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverLiveTripScreen(
    onBusClick: () -> Unit,
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var onboardCount by remember { mutableIntStateOf(25) }
    val maxCapacity = 40

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Live trip",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "B-204 • Route 12",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Surface(
                        color = Color(0xFFF0F4F8),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(modifier = Modifier.size(6.dp), color = Color(0xFF4CAF50), shape = CircleShape) {}
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("GPS", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            DriverBottomBar(
                selectedScreen = "trip",
                onBusClick = onBusClick,
                onTripClick = { },
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FBFF))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Main Trip Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF6A39FF))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(modifier = Modifier.size(6.dp), color = Color(0xFF4CAF50), shape = CircleShape) {}
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Trip active", fontSize = 12.sp, color = Color.White)
                            }
                        }
                        Text(text = "Started 9:02 AM", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TripStatItem("Speed", "38", "km/h")
                        TripStatItem("Distance", "8.4", "km")
                        TripStatItem("Onboard", "$onboardCount", "/$maxCapacity")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Map Simulation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EDFF))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Placeholder for Map drawing
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val path = Path().apply {
                            moveTo(size.width * 0.2f, size.height * 0.7f)
                            quadraticTo(
                                size.width * 0.4f, size.height * 0.5f,
                                size.width * 0.8f, size.height * 0.2f
                            )
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF6A39FF),
                            style = Stroke(width = 8f)
                        )
                    }

                    // Route Tag
                    Surface(
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Route, null, modifier = Modifier.size(14.dp), tint = Color(0xFF6A39FF))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Route 12", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                    }

                    // Bus Marker
                    Column(
                        modifier = Modifier.align(Alignment.Center).offset(x = 40.dp, y = (-20).dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            color = Color.Black,
                            shape = CircleShape
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.DirectionsBus, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                        Surface(
                            color = Color.Black,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text(
                                "B-204",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }

                    // User Marker
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart).padding(start = 40.dp, bottom = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            color = Color(0xFF6A39FF),
                            shape = CircleShape
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                        Text("You", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "QUICK ACTIONS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { if (onboardCount < maxCapacity) onboardCount++ },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A39FF))
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Increase", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { if (onboardCount > 0) onboardCount-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Icon(Icons.Default.Remove, null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Decrease", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TripInfoCard(Modifier.weight(1f), Icons.Default.Wifi, "Signal", "Strong")
                TripInfoCard(Modifier.weight(1f), Icons.Default.Timeline, "Status", "On route")
                TripInfoCard(Modifier.weight(1f), Icons.Default.Groups, "Stops", "3 left")
            }
        }
    }
}

@Composable
fun TripStatItem(label: String, value: String, unit: String) {
    Column {
        Text(text = label, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                text = unit,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
            )
        }
    }
}

@Composable
fun TripInfoCard(modifier: Modifier, icon: ImageVector, label: String, value: String) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(label, color = Color.Gray, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DriverLiveTripScreenPreview() {
    DriverLiveTripScreen(onBusClick = {}, onProfileClick = {}, onBackClick = {})
}
