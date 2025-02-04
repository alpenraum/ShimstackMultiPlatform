package com.alpenraum.shimstack.ui.homescreen

import androidx.compose.runtime.Immutable
import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.base.UnidirectionalViewModel
import com.alpenraum.shimstack.data.bike.LocalBikeRepository
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.model.cardsetup.CardSetup
import com.alpenraum.shimstack.domain.model.measurementunit.MeasurementUnitType
import com.alpenraum.shimstack.domain.userSettings.GetUserSettingsUseCase
import com.alpenraum.shimstack.ui.bottomnav.MainNavigator
import com.alpenraum.shimstack.ui.newBike.navigation.NewBikeNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeScreenViewModel(
    private val bikeRepository: LocalBikeRepository,
    private val mainNavigator: MainNavigator,
    private val newBikeNavigator: NewBikeNavigator,
    userSettingsUseCase: GetUserSettingsUseCase,
    dispatchersProvider: DispatchersProvider
) : BaseViewModel(dispatchersProvider),
    HomeScreenContract {
    private val bikes = MutableStateFlow<ImmutableList<Bike>>(persistentListOf())
    private val cardSetups = MutableStateFlow<ImmutableList<CardSetup>>(CardSetup.defaultConfig())

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<HomeScreenContract.State> =
        combine(bikes, cardSetups, userSettingsUseCase()) { bikeList, cardSetupList, userSettings ->
            Triple(bikeList, cardSetupList, userSettings.measurementUnitType)
        }.mapLatest(::createState)
            .catch {
                eventFlow.emit(HomeScreenContract.Event.Error)
                emit(HomeScreenContract.State(persistentListOf(), CardSetup.defaultConfig()))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HomeScreenContract.State(persistentListOf(), CardSetup.defaultConfig())
            )

    private val eventFlow = MutableSharedFlow<HomeScreenContract.Event>()
    override val event: SharedFlow<HomeScreenContract.Event> =
        eventFlow.asSharedFlow()

    override fun intent(
        intent: HomeScreenContract.Intent,
        navController: NavController
    ) {
        when (intent) {
            HomeScreenContract.Intent.OnRefresh -> {}

            HomeScreenContract.Intent.OnAddNewBike -> {
                viewModelScope.launch {
                    newBikeNavigator.navigateToNewBike(navController)
                }
            }

            is HomeScreenContract.Intent.OnBikeDetailsClicked ->
                viewModelScope.launch {
                    intent.bike.id?.let {
                        mainNavigator.navigateToBikeDetails(it, navController)
                    }
                }

            HomeScreenContract.Intent.OnTechnicalError ->
                viewModelScope.launch {
                    eventFlow.emit(HomeScreenContract.Event.Error)
                }
        }
    }

    fun initVm() =
        fetchBikes()
            .onStart {
                eventFlow.emit(HomeScreenContract.Event.Loading)
            }.map { it.toImmutableList() }
            .onEach {
                bikes.emit(it)
                delay(200)
                eventFlow.emit(HomeScreenContract.Event.FinishedLoading)
            }.launchIn(iOScope)

    private fun fetchBikes() = bikeRepository.getAllBikes()

    private fun createState(
        state: Triple<ImmutableList<Bike>, ImmutableList<CardSetup>, MeasurementUnitType>
    ): HomeScreenContract.State =
        HomeScreenContract.State(
            state.first,
            state.second,
            state.third == MeasurementUnitType.METRIC
        )
}

interface HomeScreenContract :
    UnidirectionalViewModel<HomeScreenContract.State, HomeScreenContract.Intent, HomeScreenContract.Event> {
    @Immutable
    data class State(
        val bikes: ImmutableList<Bike?>,
        val detailCardsSetup: ImmutableList<CardSetup>,
        val isMetric: Boolean = true
    ) {
        fun getBike(page: Int) = bikes.getOrNull(page)
    }

    sealed class Event {
        data object Loading : Event()

        data object FinishedLoading : Event()

        data object Error : Event()
    }

    sealed class Intent {
        data object OnRefresh : Intent()

        data object OnAddNewBike : Intent()

        data class OnBikeDetailsClicked(
            val bike: Bike
        ) : Intent()

        data object OnTechnicalError : Intent()
    }
}

sealed class UIDataLabel {
    class Simple(
        val heading: String,
        val content: String
    ) : UIDataLabel()

    class Complex(
        val data: Map<String, String>
    ) : UIDataLabel()
}