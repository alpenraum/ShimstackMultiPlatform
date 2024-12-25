package com.alpenraum.shimstack.base.di

import com.alpenraum.shimstack.ui.base.navigation.NavDestinationDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavGraphDefinition
import com.alpenraum.shimstack.ui.base.navigation.NavViewModel
import com.alpenraum.shimstack.ui.bottomnav.MainNavigationGraph
import com.alpenraum.shimstack.ui.bottomnav.MainRoute
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun navigationModule() =
    module {

        single(named("startDestinationRoute")) {
            MainRoute.MainRouteRoot
        } bind NavDestinationDefinition::class

        viewModel {
            NavViewModel(get(named("startDestinationRoute")))
        }
    }