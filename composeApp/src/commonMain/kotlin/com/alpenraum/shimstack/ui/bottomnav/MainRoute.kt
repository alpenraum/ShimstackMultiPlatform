package com.alpenraum.shimstack.ui.bottomnav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alpenraum.shimstack.ui.base.navigation.NavDestinationDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavGraphDefinition
import kotlinx.serialization.Serializable

class MainNavigationGraph : NavGraphDefinition<MainRoute> {
    override val startDestinationRoute: MainRoute = MainRoute.HomeScreen

    override fun build(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        createNavigation(navGraphBuilder, navController)
    }

    private fun createNavigation(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.navigation<MainRoute.MainRouteRoot>(
            startDestination = startDestinationRoute
        ) {
            composable<MainRoute.HomeScreen> {
                BottomNavFeature(navController)
            }
        }
    }
}

@Serializable
sealed class MainRoute : NavDestinationDefinition {
    @Serializable
    data object MainRouteRoot : MainRoute()

    @Serializable
    data object HomeScreen : MainRoute()
}