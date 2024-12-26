package com.alpenraum.shimstack.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.navigation
import org.koin.core.annotation.Single

@Single
class OnboardingNavigator {
    fun navigateToOnboarding(navController: NavController) {
        navController.navigate(OnboardingRoute.Welcome)
    }
}