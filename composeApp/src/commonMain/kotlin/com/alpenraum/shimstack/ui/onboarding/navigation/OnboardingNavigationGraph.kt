package com.alpenraum.shimstack.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alpenraum.shimstack.ui.base.navigation.NavDestinationDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavGraphDefinition
import com.alpenraum.shimstack.ui.onboarding.OnboardingFeature
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

@Single(binds = [NavGraphDefinition::class])
class OnboardingNavigationGraph : NavGraphDefinition<OnboardingRoute> {
    override val startDestinationRoute: OnboardingRoute = OnboardingRoute.Welcome

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
        navGraphBuilder.navigation<OnboardingRoute.OnboardingRouteRoot>(
            startDestination = startDestinationRoute
        ) {
            composable<OnboardingRoute.Welcome> {
                OnboardingFeature(navController)
            }
        }
    }
}

@Serializable
sealed class OnboardingRoute : NavDestinationDefinition {
    @Serializable
    data object OnboardingRouteRoot : OnboardingRoute()

    @Serializable
    data object Welcome : OnboardingRoute()
}