package com.alpenraum.shimstack.ui.bottomnav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alpenraum.shimstack.ui.bottomnav.navigation.BottomNavigationGraph
import com.alpenraum.shimstack.ui.bottomnav.navigation.BottomNavigationItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavFeature(navController: NavController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            BottomNavigationItem.asList().forEach {
                item(
                    icon = { Icon(painterResource(it.iconRes), contentDescription = null) },
                    label = { Text(stringResource(it.titleRes)) },
                    selected = it.route == currentRoute,
                    onClick = {
                        bottomNavController.navigate(it.route) {
                            bottomNavController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        BottomNavigationGraph(bottomNavController, navController, Modifier.fillMaxSize().safeDrawingPadding())
    }
}