@file:Suppress("DEPRECATION")

package com.example.trackmybus.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertsScreen(
    onHomeClick: () -> Unit,
    onTrackClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("All", "Unread", "Trips")

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onTrackClick,
                    icon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
                    label = { Text("Track") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                    label = { Text("Alerts") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF6A39FF),
                        selectedTextColor = Color(0xFF6A39FF),
                        indicatorColor = Color(0xFFF0EDFF)
                    )
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
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Alerts",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Color(0xFF6A39FF),
                edgePadding = 0.dp,
                divider = {},
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFF6A39FF)
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {
                    Text(
                        text = "Today",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(alertList.filter { it.isToday }) { alert ->
                    AlertItem(alert)
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Yesterday",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(alertList.filter { !it.isToday }) { alert ->
                    AlertItem(alert)
                }
            }
        }
    }
}

@Composable
fun AlertItem(alert: AlertData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = alert.iconBg
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = alert.icon,
                        contentDescription = null,
                        tint = alert.iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alert.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = alert.time,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            if (alert.isUnread) {
                Surface(
                    modifier = Modifier.size(8.dp),
                    shape = CircleShape,
                    color = Color(0xFF6A39FF)
                ) {}
                Spacer(modifier = Modifier.width(8.dp))
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

data class AlertData(
    val title: String,
    val time: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val isToday: Boolean,
    val isUnread: Boolean = false
)

val alertList = listOf(
    AlertData(
        "Bus B-204 is approaching Library",
        "2 min ago",
        Icons.Default.DirectionsBus,
        Color(0xFFF0EDFF),
        Color(0xFF6A39FF),
        true,
        true
    ),
    AlertData(
        "Route 12 schedule updated",
        "1 hr ago",
        Icons.Default.Schedule,
        Color(0xFFFFF3E0),
        Color(0xFFF57C00),
        true
    ),
    AlertData(
        "Heavy traffic on College Road",
        "3 hrs ago",
        Icons.Default.Warning,
        Color(0xFFFDE7E9),
        Color(0xFFE53935),
        true
    ),
    AlertData(
        "Bus B-205 is now at Main Gate",
        "Yesterday, 08:30 AM",
        Icons.Default.DirectionsBus,
        Color(0xFFF0EDFF),
        Color(0xFF6A39FF),
        false
    ),
    AlertData(
        "Weekly trip report available",
        "Yesterday, 06:00 PM",
        Icons.Default.Assessment,
        Color(0xFFE8F5E9),
        Color(0xFF4CAF50),
        false
    )
)

@Preview(showBackground = true)
@Composable
fun AlertsScreenPreview() {
    AlertsScreen(onHomeClick = {}, onTrackClick = {}, onProfileClick = {})
}
