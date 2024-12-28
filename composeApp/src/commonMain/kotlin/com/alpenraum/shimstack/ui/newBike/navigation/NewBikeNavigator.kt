package com.alpenraum.shimstack.ui.newBike.navigation

import androidx.navigation.NavController
import org.koin.core.annotation.Single

interface NewBikeNavigator {
    fun navigateToNewBike(navController: NavController)
}

@Single
class NewBikeNavigatorImpl : NewBikeNavigator {
    override fun navigateToNewBike(navController: NavController) {
        navController.navigate(NewBikeRoute.NewBikeRouteRoot)
    }
}