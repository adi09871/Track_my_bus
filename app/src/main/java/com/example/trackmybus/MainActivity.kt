package com.example.trackmybus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackmybus.ui.theme.TrackMyBusTheme
import com.example.trackmybus.userinterface.OptionScreen
import com.example.trackmybus.userinterface.splashscreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackMyBusTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splashscreen"
                ) {
                    composable("splashscreen") {
                        splashscreen(onTimeout = {
                            navController.navigate("optionscreen") {
                                popUpTo("splashscreen") { inclusive = true }
                            }
                        })
                    }

                    composable("optionscreen") {
                        OptionScreen()
                    }
                }
            }
        }
    }
}
