package com.alpenraum.shimstack.ui.bottomnav.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.home_24px
import shimstackmultiplatform.composeapp.generated.resources.settings
import shimstackmultiplatform.composeapp.generated.resources.title_home

sealed class BottomNavigationItem(
    val route: String,
    var titleRes: StringResource,
    var iconRes: DrawableResource
) {
    data object Home : BottomNavigationItem(
        "home",
        Res.string.title_home,
        Res.drawable.home_24px
    )

    data object Settings :
        BottomNavigationItem("settings", Res.string.settings, Res.drawable.home_24px)

    companion object {
        fun asList() = listOf(Home, Settings)
    }
}