package com.alpenraum.shimstack.ui.base.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavGraphDefinition<T : NavDestinationDefinition> {
    val startDestinationRoute: T

    fun build(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    )
}