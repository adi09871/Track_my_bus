package com.aditya.trackmybus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aditya.trackmybus.model.SaveStopsRequest
import com.aditya.trackmybus.network.RetrofitInstance
import com.aditya.trackmybus.session.SessionManager
import com.aditya.trackmybus.theme.ThemeManager
import com.aditya.trackmybus.ui.theme.TrackMyBusTheme
import com.aditya.trackmybus.userinterface.AddBusScreen
import com.aditya.trackmybus.userinterface.AddStopsScreen
import com.aditya.trackmybus.userinterface.AlertsScreen
import com.aditya.trackmybus.userinterface.BusTrackingScreen
import com.aditya.trackmybus.userinterface.DriverHome
import com.aditya.trackmybus.userinterface.DriverLogin
import com.aditya.trackmybus.userinterface.DriverProfileScreen
import com.aditya.trackmybus.userinterface.DriverSignup
import com.aditya.trackmybus.userinterface.OptionScreen
import com.aditya.trackmybus.userinterface.ProfileScreen
import com.aditya.trackmybus.userinterface.StudentHome
import com.aditya.trackmybus.userinterface.StudentLogin
import com.aditya.trackmybus.userinterface.StudentSignup
import com.aditya.trackmybus.userinterface.splashscreen
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.init(this)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->

                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }

                val token = task.result

                println(
                    "FCM TOKEN = $token"
                )
            }
        enableEdgeToEdge()
        setContent {
            val themeMode by ThemeManager.themeMode.collectAsState()
            TrackMyBusTheme(themeMode = themeMode) {
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
                        OptionScreen(
                            onStudentClick = { navController.navigate("studentlogin") },
                            onDriverClick = { navController.navigate("driverlogin") }
                        )
                    }

                    composable("studentlogin") {
                        StudentLogin(
                            onBackClick = { navController.popBackStack() },
                            onLoginSuccess = {
                                navController.navigate("studenthome") {
                                    popUpTo("optionscreen") { inclusive = true }
                                }
                            },
                            onSignupClick = { navController.navigate("studentsignup") }
                        )
                    }

                    composable("studentsignup") {
                        StudentSignup(
                            onBackClick = { navController.popBackStack() },
                            onSignupSuccess = {
                                navController.navigate("studentlogin") {
                                    popUpTo("studentsignup") { inclusive = true }
                                }

                            }
                        )
                    }

                    composable("driverlogin") {
                        DriverLogin(
                            onBackClick = { navController.popBackStack() },
                            onLoginSuccess = {
                                navController.navigate("addbus") {
                                    popUpTo("optionscreen") { inclusive = true }
                                }
                            },
                            onSignupClick = { navController.navigate("driversignup") }
                        )
                    }

                    composable("driversignup") {
                        DriverSignup(
                            onBackClick = { navController.popBackStack() },
                            onSignupSuccess = {
                                navController.navigate("addbus") {
                                    popUpTo("optionscreen") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("addbus") {
                        AddBusScreen(
                            onBackClick = { navController.popBackStack() },
                            onCreateBusClick = { busNum, cap, route ->
                                navController.navigate("addstops/$busNum/$route/$cap") {
                                    popUpTo("addbus") { inclusive = true }
                                }
                            },
                            onBusSelect = { busNum, route, cap, id ->
                                navController.navigate("addstops/$busNum/$route/$cap") {
                                    popUpTo("addbus") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("addstops/{busNumber}/{routeName}/{seatCapacity}") { backStackEntry ->
                        val busNumber = backStackEntry.arguments?.getString("busNumber") ?: ""
                        val routeName = backStackEntry.arguments?.getString("routeName") ?: ""
                        val seatCapacity = backStackEntry.arguments?.getString("seatCapacity")?.toIntOrNull() ?: 0

                        AddStopsScreen(
                            busNumber = busNumber,
                            routeName = routeName,
                            seatCapacity = seatCapacity,
                            onBackClick = { navController.popBackStack() },
                            onSaveStopsClick = { stops ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {

                                        val response = RetrofitInstance.api.saveStops(
                                            SaveStopsRequest(
                                                busId = SessionManager.busId,
                                                stops = stops
                                            )
                                        )

                                        if (response.isSuccessful) {
                                            launch(Dispatchers.Main) {
                                                navController.navigate("driverhome") {
                                                    popUpTo("addbus") { inclusive = true }
                                                }
                                            }
                                        }
                                    } catch (e: Exception) {
                                        // Error saving stops
                                    }
                                }
                            }
                        )
                    }

                    composable("studenthome") {
                        StudentHome(
                            onTrackBusClick = { navController.navigate("bustracking") },
                            onTrackClick = { navController.navigate("bustracking") },
                            onAlertsClick = { navController.navigate("alerts") },
                            onProfileClick = { navController.navigate("profile") }
                        )
                    }

                    composable("bustracking") {
                        BusTrackingScreen(
                            onHomeClick = { navController.navigate("studenthome") },
                            onAlertsClick = { navController.navigate("alerts") },
                            onProfileClick = { navController.navigate("profile") },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable("alerts") {
                        AlertsScreen(
                            onHomeClick = { navController.navigate("studenthome") },
                            onTrackClick = { navController.navigate("bustracking") },
                            onProfileClick = { navController.navigate("profile") }
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            onHomeClick = {
                                navController.navigate("studenthome") {
                                    popUpTo("studenthome") { inclusive = true }
                                }
                            },
                            onTrackClick = { navController.navigate("bustracking") },
                            onAlertsClick = { navController.navigate("alerts") },
                            onLogoutClick = {
                                navController.navigate("optionscreen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("driverhome") {
                        DriverHome(
                            onProfileClick = {
                                navController.navigate("driverprofile") {
                                    popUpTo("driverhome") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }

                    composable("driverprofile") {
                        DriverProfileScreen(
                            onBusClick = {
                                navController.navigate("driverhome") {
                                    popUpTo("driverhome") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            onLogoutClick = {
                                navController.navigate("optionscreen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
