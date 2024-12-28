package com.alpenraum.shimstack.ui.onboarding

import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.data.datastore.ShimstackDatastore
import com.alpenraum.shimstack.ui.base.navigation.popUpToNavOptions
import com.alpenraum.shimstack.ui.bottomnav.MainNavigator
import com.alpenraum.shimstack.ui.newBike.navigation.NewBikeNavigator
import com.alpenraum.shimstack.ui.onboarding.navigation.OnboardingRoute
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class OnboardingViewModel(
    dispatchersProvider: DispatchersProvider,
    private val dataStore: ShimstackDatastore,
    private val mainNavigator: MainNavigator,
    private val newBikeNavigator: NewBikeNavigator
) : BaseViewModel(dispatchersProvider) {
    private fun onHomeNavigationClicked(navController: NavController) {
        val options = popUpToNavOptions<OnboardingRoute.OnboardingRouteRoot>()

        mainNavigator.navigateToHome(navController, navOptions = options)
    }

    fun onAddBikeNavigationClicked(navController: NavController) {
        newBikeNavigator.navigateToNewBike(navController)
    }

    fun onSkipClicked(navController: NavController) {
        viewModelScope.launch {
            dataStore.setIsOnboardingCompleted(true)
            onHomeNavigationClicked(navController)
        }
    }
}