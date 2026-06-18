@file:Suppress("DEPRECATION")

package com.example.trackmybus.userinterface

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackmybus.R
import com.example.trackmybus.session.SessionManager
import com.example.trackmybus.viewmodel.AlertsViewModel

@Composable
fun AlertsScreen(
    onHomeClick: () -> Unit,
    onTrackClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: AlertsViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadNotifications(SessionManager.studentId)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
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
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.tertiary
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
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Alerts",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (viewModel.notifications.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp).alpha(0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No notifications available",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(viewModel.notifications) { notification ->
                        val icon = when {
                            notification.title.contains("Trip", ignoreCase = true) -> Icons.Default.DirectionsBus
                            notification.title.contains("Schedule", ignoreCase = true) -> Icons.Default.Schedule
                            notification.title.contains("Traffic", ignoreCase = true) || notification.title.contains("Warning", ignoreCase = true) -> Icons.Default.Warning
                            else -> Icons.Default.Notifications
                        }

                        AlertItem(
                            AlertData(
                                title = notification.message,
                                time = notification.title,
                                icon = icon,
                                iconBg = MaterialTheme.colorScheme.tertiary,
                                iconTint = MaterialTheme.colorScheme.primary,
                                isToday = true,
                                isUnread = !notification.isRead
                            )
                        )
                    }
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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
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
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = alert.time,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (alert.isUnread) {
                Surface(
                    modifier = Modifier.size(8.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {}
                Spacer(modifier = Modifier.width(8.dp))
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
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

@Preview(showBackground = true)
@Composable
fun AlertsScreenPreview() {
    AlertsScreen(onHomeClick = {}, onTrackClick = {}, onProfileClick = {})
}
