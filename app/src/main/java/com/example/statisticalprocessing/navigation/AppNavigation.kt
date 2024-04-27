package com.example.statisticalprocessing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.statisticalprocessing.screens.Graph
import com.example.statisticalprocessing.screens.RandomEvents

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.RandomEvents.route) {
        composable(route = AppScreens.RandomEvents.route) {
            RandomEvents(navController)
        }
        composable(
            route = AppScreens.Graph.route + "/{prob1}/{prob2}/{prob3}/{prob4}/{prob5}/{trials}",
            arguments = listOf(
                navArgument("prob1") { type = NavType.FloatType },
                navArgument("prob2") { type = NavType.FloatType },
                navArgument("prob3") { type = NavType.FloatType },
                navArgument("prob4") { type = NavType.FloatType },
                navArgument("prob5") { type = NavType.FloatType },
                navArgument("trials") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            Graph(
                navController,
                prob1 = backStackEntry.arguments?.getFloat("prob1") ?: 0.0F,
                prob2 = backStackEntry.arguments?.getFloat("prob2") ?: 0.0F,
                prob3 = backStackEntry.arguments?.getFloat("prob3") ?: 0.0F,
                prob4 = backStackEntry.arguments?.getFloat("prob4") ?: 0.0F,
                prob5 = backStackEntry.arguments?.getFloat("prob5") ?: 0.0F,
                trials = backStackEntry.arguments?.getInt("trials") ?: 0
            )
        }
    }
}