package com.alpenraum.shimstack.ui.bottomnav

import androidx.navigation.NavController
import org.koin.core.annotation.Single

@Single
class MainNavigator {
    fun navigateToBikeDetails(
        id: Int,
        navController: NavController
    ) {
        navController.navigate(MainRoute.BikeDetails(id))
    }
}