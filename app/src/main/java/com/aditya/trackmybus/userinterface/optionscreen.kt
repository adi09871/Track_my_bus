package com.aditya.trackmybus.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.aditya.trackmybus.R
import com.aditya.trackmybus.theme.ThemeManager
import com.aditya.trackmybus.theme.ThemeMode

@Composable
fun OptionScreen(onStudentClick: () -> Unit, onDriverClick: () -> Unit) {
    val themeMode by ThemeManager.themeMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Surface(
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(28.dp),
            shadowElevation = 8.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Smart Bus Tracker",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Real-time college transportation, designed for students and drivers.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Theme Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "☀ Light",
                fontSize = 14.sp,
                fontWeight = if (themeMode == ThemeMode.LIGHT) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = themeMode == ThemeMode.DARK,
                onCheckedChange = { isDark ->
                    ThemeManager.setThemeMode(if (isDark) ThemeMode.DARK else ThemeMode.LIGHT)
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Text(
                text = "🌙 Dark",
                fontSize = 14.sp,
                fontWeight = if (themeMode == ThemeMode.DARK) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            TextButton(onClick = { ThemeManager.setThemeMode(ThemeMode.SYSTEM) }) {
                Text(
                    text = "System",
                    fontWeight = if (themeMode == ThemeMode.SYSTEM) FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CONTINUE AS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        SelectionCard(
            title = "Student",
            description = "Track your assigned bus",
            icon = Icons.Default.Person,
            iconBgColor = MaterialTheme.colorScheme.tertiary,
            iconTint = MaterialTheme.colorScheme.primary,
            onClick = onStudentClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectionCard(
            title = "Driver",
            description = "Manage your trip & route",
            icon = Icons.Default.DirectionsBus,
            iconBgColor = MaterialTheme.colorScheme.tertiary,
            iconTint = MaterialTheme.colorScheme.primary,
            onClick = onDriverClick
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OptionScreenPreview() {
    OptionScreen(onStudentClick = {}, onDriverClick = {})
}

@Composable
fun SelectionCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = iconBgColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
