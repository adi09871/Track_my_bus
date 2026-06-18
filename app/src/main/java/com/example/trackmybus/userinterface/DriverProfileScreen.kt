package com.example.trackmybus.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.trackmybus.viewmodel.DriverProfileViewModel

@Composable
fun DriverProfileScreen(
    onBusClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: DriverProfileViewModel = viewModel()
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (SessionManager.driverId != -1L) {
            viewModel.fetchProfile(SessionManager.driverId)
        }
    }

    if (showLogoutDialog) {
        BrandedConfirmDialog(
            title = "Log out",
            message = "Are you sure you want to log out from the application?",
            onConfirm = {
                showLogoutDialog = false
                onLogoutClick()
            },
            onDismiss = { showLogoutDialog = false },
            confirmText = "Log out"
        )
    }

    Scaffold(
        bottomBar = {
            DriverBottomBar(
                selectedScreen = "profile",
                onBusClick = onBusClick,
                onProfileClick = { }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if (SessionManager.driverId == -1L) {
                Text(
                    text = "Please log in to view your profile",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (viewModel.error != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { viewModel.fetchProfile(SessionManager.driverId) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Retry", color = Color.White)
                    }
                }
            } else if (viewModel.profileData != null) {
                val driver = viewModel.profileData!!
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    // Avatar
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = getDriverInitials(driver.name),
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Details Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column {
                            ProfileDetailRow(Icons.Default.Person, "Name", driver.name)
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                            )
                            ProfileDetailRow(Icons.Default.Email, "Email", driver.email)
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    // Logout Button
                    Button(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFDE7E9)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Log out",
                            color = Color(0xFFE53935),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Smart Bus Tracker • v1.0.0",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

fun getDriverInitials(name: String): String {
    if (name.isBlank()) return "?"
    val words = name.trim().split("\\s+".toRegex())
    return if (words.size >= 2) {
        (words[0].take(1) + words[1].take(1)).uppercase()
    } else {
        words[0].take(1).uppercase()
    }
}

@Preview(showBackground = true)
@Composable
fun DriverProfileScreenPreview() {
    DriverProfileScreen(onBusClick = {}, onLogoutClick = {})
}
