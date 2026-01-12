package com.example.agendagora.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")

    // Agendamento tem um argumento: serviceId
    object Agendamento : Screen("agendamento/{serviceId}") {
        fun createRoute(serviceId: String) = "agendamento/$serviceId"
    }

    object MeusAgendamentos : Screen("meus_agendamentos")
}
