package com.alpenraum.shimstack.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.alpenraum.shimstack.ui.base.navigation.NavDestinationDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavGraphDefinition
import kotlinx.serialization.Serializable

class MainNavigationGraph : NavGraphDefinition<MainRoute> {
    override val startDestinationRoute: MainRoute = MainRoute.HomeScreen

    override fun build(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
    ) {
        createNavigation(navGraphBuilder, navController)
    }

    private fun createNavigation(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
    ) {
        navGraphBuilder.navigation<MainRoute.MainRouteRoot>(
            startDestination = startDestinationRoute,
        ) {
            composable<MainRoute.HomeScreen> {
                Column {
                    Text("HomeScreen")

                    Button(onClick = { navController.navigate(MainRoute.SettingsScreen("from homeScreen")) }) {
                        Text("click me")
                    }
                }
            }

            composable<MainRoute.SettingsScreen> {
                val navArgs = it.toRoute<MainRoute.SettingsScreen>()
                Text("Settings: ${navArgs.setting}")
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

    @Serializable
    data class SettingsScreen(
        val setting: String,
    ) : MainRoute()
}
