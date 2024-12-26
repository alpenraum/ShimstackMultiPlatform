package com.alpenraum.shimstack.ui.bottomnav

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.alpenraum.shimstack.ui.base.navigation.NavDestinationDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavGraphDefinition
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

@Single(binds = [NavGraphDefinition::class])
class MainNavigationGraph : NavGraphDefinition<MainRoute> {
    override val startDestinationRoute: MainRoute = MainRoute.BottomNav

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
            composable<MainRoute.BottomNav>(
                deepLinks =
                    listOf(
                        navDeepLink<MainRoute.BottomNav>("hahaha.at/lol")
                    )
            ) {
                BottomNavFeature(navController)
            }

            composable<MainRoute.BikeDetails> {
                val id = it.toRoute<MainRoute.BikeDetails>().id
                Text("Bike details: $id")
            }
        }
    }
}

@Serializable
sealed class MainRoute : NavDestinationDefinition {
    @Serializable
    data object MainRouteRoot : MainRoute()

    @Serializable
    data object BottomNav : MainRoute()

    @Serializable
    data class BikeDetails(
        val id: Int
    ) : MainRoute()
}