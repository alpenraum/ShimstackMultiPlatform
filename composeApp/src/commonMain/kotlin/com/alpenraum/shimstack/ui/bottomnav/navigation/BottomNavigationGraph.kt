package com.alpenraum.shimstack.ui.bottomnav.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alpenraum.shimstack.ui.base.compose.fadeIn
import com.alpenraum.shimstack.ui.base.compose.fadeOut
import com.alpenraum.shimstack.ui.homescreen.HomeScreenFeature

@Composable
fun BottomNavigationGraph(
    bottomNavController: NavHostController,
    featureNavController: NavController,
    modifier: Modifier = Modifier
) {
    NavHost(
        bottomNavController,
        startDestination = BottomNavigationItem.Home.route,
        modifier = modifier,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popExitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        }
    ) {
        composable(BottomNavigationItem.Home.route) {
            HomeScreenFeature(navController = featureNavController)
        }
        composable(BottomNavigationItem.Settings.route) {
            Text("Settings")
            // SettingsScreen(navController = featureNavController)
        }
    }
}