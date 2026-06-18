package com.aditya.trackmybus.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aditya.trackmybus.R
import com.aditya.trackmybus.ui.theme.TrackMyBusTheme
import kotlinx.coroutines.delay

@Composable
fun splashscreen (onTimeout: () -> Unit){

    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
        contentAlignment =  Alignment.Center)   {

        Surface(
            modifier = Modifier.size(120.dp),
            color = Color.White, // Logo container usually looks best in white
            shape = RoundedCornerShape(32.dp),
            shadowElevation = 8.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image (
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Splash Screen",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TrackMyBusTheme {
        splashscreen(onTimeout = {})
    }
}
