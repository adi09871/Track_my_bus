package com.example.trackmybus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackmybus.ui.theme.TrackMyBusTheme
import com.example.trackmybus.userinterface.AddBusScreen
import com.example.trackmybus.userinterface.DriverLogin
import com.example.trackmybus.userinterface.DriverSignup
import com.example.trackmybus.userinterface.OptionScreen
import com.example.trackmybus.userinterface.AlertsScreen
import com.example.trackmybus.userinterface.BusTrackingScreen
import com.example.trackmybus.userinterface.DriverHome
import com.example.trackmybus.userinterface.DriverLiveTripScreen
import com.example.trackmybus.userinterface.DriverProfileScreen
import com.example.trackmybus.userinterface.ProfileScreen
import com.example.trackmybus.userinterface.StudentHome
import com.example.trackmybus.userinterface.StudentLogin
import com.example.trackmybus.userinterface.StudentSignup
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
                                navController.navigate("studenthome") {
                                    popUpTo("optionscreen") { inclusive = true }
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
                                // Save bus details logic can be added here
                                navController.navigate("driverhome") {
                                    popUpTo("addbus") { inclusive = true }
                                }
                            },
                            onBusSelect = { busName ->
                                navController.navigate("driverhome") {
                                    popUpTo("addbus") { inclusive = true }
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
                            },
                            onTripClick = {
                                navController.navigate("drivertrip") {
                                    popUpTo("driverhome") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }

                    composable("drivertrip") {
                        DriverLiveTripScreen(
                            onBusClick = {
                                navController.navigate("driverhome") {
                                    popUpTo("driverhome") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            onProfileClick = {
                                navController.navigate("driverprofile") {
                                    popUpTo("driverhome") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            onBackClick = { navController.popBackStack() }
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
                            onTripClick = {
                                navController.navigate("drivertrip") {
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
