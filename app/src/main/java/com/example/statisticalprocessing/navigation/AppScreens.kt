package com.example.statisticalprocessing.navigation

sealed class AppScreens (val route: String){
    data object RandomEvents: AppScreens("random_events_screen")
    data object Graph: AppScreens("graph_screen")
}