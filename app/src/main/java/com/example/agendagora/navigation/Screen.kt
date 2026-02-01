package com.example.agendagora.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object NewAppointment : Screen("new_appointment")

    object ServicesAvailable : Screen("services_available")
}