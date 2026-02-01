package com.example.agendagora.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendagora.ui.splash.SplashScreen
import com.example.agendagora.ui.auth.LoginScreen
import com.example.agendagora.ui.auth.RegisterScreen
import com.example.agendagora.ui.home.HomeScreen
import com.example.agendagora.ui.appointment.NewAppointmentScreen
import com.example.agendagora.ui.services.ServicesAvailableScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.NewAppointment.route) {
            NewAppointmentScreen(navController)
        }
        composable(Screen.ServicesAvailable.route) {
            ServicesAvailableScreen(navController)
        }

        composable(
            route = Screen.NewAppointment.route,
            arguments = listOf(navArgument("selectedService") { defaultValue = "" })
        ) { backStackEntry ->
            val selectedService = backStackEntry.arguments?.getString("selectedService") ?: ""
            NewAppointmentScreen(navController, preSelectedService = selectedService)
        }
    }
}
