package com.alpenraum.shimstack.ui.onboarding

import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.data.datastore.ShimstackDatastore
import com.alpenraum.shimstack.ui.base.navigation.popUpToNavOptions
import com.alpenraum.shimstack.ui.bottomnav.MainNavigator
import com.alpenraum.shimstack.ui.onboarding.navigation.OnboardingRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    dispatchersProvider: DispatchersProvider,
    private val dataStore: ShimstackDatastore,
    private val mainNavigator: MainNavigator
    // private val newBikeNavigator: NewBikeNavigator
) : BaseViewModel(dispatchersProvider) {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _event = MutableSharedFlow<Event>()

    fun event() = _event.asSharedFlow()

    sealed class Event {
        data object NavigateToHomeScreen : Event()
    }

    private fun onHomeNavigationClicked(navController: NavController) {
        val options = popUpToNavOptions<OnboardingRoute.OnboardingRouteRoot>()

        mainNavigator.navigateToHome(navController, navOptions = options)
    }

    fun onAddBikeNavigationClicked(navController: NavController) {
        // newBikeNavigator.navigateToNewBike(navController) TODO
    }

    fun onSkipClicked(navController: NavController) {
        viewModelScope.launch {
            dataStore.setIsOnboardingCompleted(true)
            onHomeNavigationClicked(navController)
        }
    }
}