package com.example.agendagora.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.agendagora.ui.screens.agendamento.AgendamentoScreen
import com.example.agendagora.ui.screens.auth.LoginScreen
import com.example.agendagora.ui.screens.auth.RegisterScreen
import com.example.agendagora.ui.screens.home.HomeScreen
import com.example.agendagora.ui.screens.meusagendamentos.MeusAgendamentosScreen
import com.example.agendagora.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinish = { isLoggedIn ->
                if (isLoggedIn) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToAgendamento = { serviceId ->
                    navController.navigate(Screen.Agendamento.createRoute(serviceId))
                },
                onNavigateToMeusAgendamentos = {
                    navController.navigate(Screen.MeusAgendamentos.route)
                }
            )
        }

        composable(
            route = Screen.Agendamento.route,
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId") ?: ""
            AgendamentoScreen(
                serviceId = serviceId,
                onSuccess = {
                    navController.navigate(Screen.MeusAgendamentos.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.MeusAgendamentos.route) {
            MeusAgendamentosScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
