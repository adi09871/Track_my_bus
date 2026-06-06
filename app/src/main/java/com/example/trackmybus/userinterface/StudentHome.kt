package com.example.trackmybus.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentHome(
    onTrackBusClick: () -> Unit,
    onTrackClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0061D5),
                        selectedTextColor = Color(0xFF0061D5),
                        indicatorColor = Color(0xFFE3F2FD)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onTrackClick,
                    icon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
                    label = { Text("Track") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onAlertsClick,
                    icon = { Icon(Icons.Outlined.Notifications, contentDescription = null) },
                    label = { Text("Alerts") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
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
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Smart Bus Tracker",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                        Surface(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd),
                            color = Color.Red,
                            shape = CircleShape
                        ) {}
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onProfileClick() }
                    )
                }
            }

            Text(text = "Good morning,", color = Color.Gray, fontSize = 16.sp)
            Text(
                text = "Aarav 👋",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0061D5))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(modifier = Modifier.size(6.dp), color = Color.Green, shape = CircleShape) {}
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("On the way", color = Color.White, fontSize = 12.sp)
                            }
                        }
                        Text("Route 12", color = Color.White, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Bus number", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                            Text("B-204", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                        }
                        Surface(
                            modifier = Modifier.size(56.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.DirectionsBus, null, tint = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoItem(Icons.Default.Schedule, "ETA", "8 min")
                        InfoItem(Icons.Default.LocationOn, "Next stop", "Library")
                        InfoItem(Icons.Default.Group, "Seats", "25/40")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onTrackBusClick() },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Icon(Icons.Default.NearMe, null, tint = Color(0xFF0061D5))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Track My Bus", color = Color(0xFF0061D5), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SmallActionCard(Modifier.weight(1f), "My route", "12 stops • 14 km")
                SmallActionCard(Modifier.weight(1f), "Schedule", "6 trips today")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent updates", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = { }) {
                    Text("See all", color = Color(0xFF0061D5))
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    UpdateItem(
                        icon = Icons.Default.Notifications,
                        iconBg = Color(0xFFE3F2FD),
                        title = "Bus B-204 is approaching Library",
                        time = "2 min ago"
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0))
                    UpdateItem(
                        icon = Icons.Default.Notifications,
                        iconBg = Color(0xFFFFF3E0),
                        title = "Route 12 schedule updated",
                        time = "1 hr ago"
                    )
                }
            }
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {
    Surface(
        color = Color.White.copy(alpha = 0.1f),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, modifier = Modifier.size(14.dp), tint = Color.White.copy(alpha = 0.7f))
                Spacer(modifier = Modifier.width(4.dp))
                Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
            }
            Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SmallActionCard(modifier: Modifier, title: String, subtitle: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun UpdateItem(icon: ImageVector, iconBg: Color, title: String, time: String) {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = iconBg) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = Color(0xFF0061D5))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
        Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
    }
}

@Preview(showBackground = true)
@Composable
fun StudentHomePreview() {
    StudentHome(
        onTrackBusClick = {},
        onTrackClick = {},
        onAlertsClick = {},
        onProfileClick = {}
    )
}
