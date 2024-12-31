package com.alpenraum.shimstack.ui.newBike.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alpenraum.shimstack.ui.base.navigation.NavDestinationDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavGraphDefinition
import com.alpenraum.shimstack.ui.newBike.NewBikeFeature
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

@Single
class NewBikeNavGraph : NavGraphDefinition<NewBikeRoute> {
    override val startDestinationRoute: NewBikeRoute = NewBikeRoute.NewBike

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
        navGraphBuilder.navigation<NewBikeRoute.NewBikeRouteRoot>(
            startDestination = startDestinationRoute
        ) {
            composable<NewBikeRoute.NewBike> {
                NewBikeFeature(navController)
            }
        }
    }
}

@Serializable
sealed class NewBikeRoute : NavDestinationDefinition {
    @Serializable
    data object NewBikeRouteRoot : NewBikeRoute()

    @Serializable
    data object NewBike : NewBikeRoute()
}