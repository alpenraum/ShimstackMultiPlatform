package com.alpenraum.shimstack.ui.onboarding

import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.BuildInfo
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.base.round
import com.alpenraum.shimstack.data.datastore.ShimstackDatastore
import com.alpenraum.shimstack.domain.bikeservice.BikeRepository
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.model.bike.BikeType
import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.model.suspension.Damping
import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.model.tire.Tire
import com.alpenraum.shimstack.ui.base.navigation.popUpToNavOptions
import com.alpenraum.shimstack.ui.bottomnav.MainNavigator
import com.alpenraum.shimstack.ui.newBike.navigation.NewBikeNavigator
import com.alpenraum.shimstack.ui.onboarding.navigation.OnboardingRoute
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.math.round
import kotlin.random.Random

@KoinViewModel
class OnboardingViewModel(
    dispatchersProvider: DispatchersProvider,
    private val dataStore: ShimstackDatastore,
    private val mainNavigator: MainNavigator,
    private val newBikeNavigator: NewBikeNavigator,
    private val bikeRepository: BikeRepository
) : BaseViewModel(dispatchersProvider) {
    private fun onHomeNavigationClicked(navController: NavController) {
        val options = popUpToNavOptions<OnboardingRoute.OnboardingRouteRoot>()

        mainNavigator.navigateToHome(navController, navOptions = options)
    }

    fun onAddBikeNavigationClicked(navController: NavController) {
        newBikeNavigator.navigateToNewBike(navController)
    }

    fun onAutoFillClick(navController: NavController) =
        viewModelScope.launch {
            if (BuildInfo.isDebug()) {
                repeat(4) {
                    val rand = Random(it)
                    val bike =
                        Bike(
                            name = "Test bike $it",
                            frontTire =
                                generateTestTire(rand),
                            rearTire = generateTestTire(rand),
                            isEBike = rand.nextBoolean(),
                            type = BikeType.fromId(rand.nextInt(0, 8)),
                            frontSuspension =
                                generateTestSuspension(rand),
                            rearSuspension =
                                if (rand.nextBoolean()) {
                                    generateTestSuspension(rand)
                                } else {
                                    null
                                }
                        )
                    try {
                        bikeRepository.createBike(bike)
                    } catch (e: Throwable) {
                        // whatever
                    }
                }
                onSkipClicked(navController)
            }
        }

    fun onSkipClicked(navController: NavController) {
        viewModelScope.launch {
            dataStore.setIsOnboardingCompleted(true)
            onHomeNavigationClicked(navController)
        }
    }
}

private fun generateTestSuspension(rand: Random) =
    Suspension(
        Pressure(rand.nextDoubleRounded(8.0, 20.0)),
        rand.nextDoubleRounded(0.2, 0.34),
        Damping(5, 5),
        Damping(7, null),
        rand.nextInt(0, 5),
        Distance(rand.nextDoubleRounded(80.0, 210.0))
    )

private fun generateTestTire(rand: Random) =
    Tire(
        Pressure(rand.nextDoubleRounded(1.0, 2.5)),
        Distance(rand.nextDoubleRounded(25.0, 50.0)),
        Distance(rand.nextDoubleRounded(0.0, 30.0))
    )

private fun Random.nextDoubleRounded(
    from: Double,
    until: Double,
    decimalPlaces: Int = 1
) = nextDouble(from, until).round(decimalPlaces)