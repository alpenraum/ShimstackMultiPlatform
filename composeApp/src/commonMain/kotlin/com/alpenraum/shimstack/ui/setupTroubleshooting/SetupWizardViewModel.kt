package com.alpenraum.shimstack.ui.setupTroubleshooting

import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.base.UnidirectionalViewModel
import com.alpenraum.shimstack.domain.SetupRecommendationRepository
import com.alpenraum.shimstack.domain.bikeservice.BikeRepository
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.troubleshooting.GetSetupSolutionUseCase
import com.alpenraum.shimstack.domain.troubleshooting.SetupRecommendation
import com.alpenraum.shimstack.domain.troubleshooting.SetupSymptom
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SetupWizardViewModel(
    private val bikeRepository: BikeRepository,
    private val setupRecommendationRepository: SetupRecommendationRepository,
    private val setupRecommendationViewMapper: SetupRecommendationViewMapper,
    private val setupSymptomViewMapper: SetupSymptomViewMapper,
    private val getSetupSolutionUseCase: GetSetupSolutionUseCase,
    dispatchersProvider: DispatchersProvider
) : BaseViewModel(dispatchersProvider),
    SetupWizardContract {
    private val bikes = MutableStateFlow<ImmutableList<Bike>>(persistentListOf())
    private var selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(null)
    private var setupRecommendations: List<SetupRecommendation> = emptyList()

    fun initVm() {
        fetchBikes()
            .map { it.toImmutableList() }
            .onEach {
                bikes.emit(it)
                emitDefaultState()
            }.launchIn(iOScope)
    }

    private fun fetchBikes() = bikeRepository.getAllBikes()

    private val _event = MutableSharedFlow<SetupWizardContract.Event>()

    private val _state: MutableStateFlow<SetupWizardContract.State> =
        MutableStateFlow(SetupWizardContract.State.Start(persistentListOf()))

    override val state: StateFlow<SetupWizardContract.State> = _state.asStateFlow()

    override val event: SharedFlow<SetupWizardContract.Event>
        get() = _event.asSharedFlow()

    override fun intent(
        intent: SetupWizardContract.Intent,
        navController: NavController
    ) {
        when (intent) {
            is SetupWizardContract.Intent.OnSelectedBikeChanged -> {
                updateSelectedBike(intent.index)
            }

            SetupWizardContract.Intent.OnSeePreviousRecommendationsClick -> {} // TODO
            SetupWizardContract.Intent.OnStartTroubleshootClick -> emitSelectSymptomState()
            is SetupWizardContract.Intent.OnSymptomSelected -> toggleSelectedSymptom(intent.symptom)
            SetupWizardContract.Intent.OnBottomSheetDismissed ->
                viewModelScope.launch {
                    emitDefaultState()
                }

            SetupWizardContract.Intent.OnRecommendationAccepted ->
                iOScope.launch {
                    TODO()
                }

            SetupWizardContract.Intent.OnRecommendationDeclined -> TODO()
            is SetupWizardContract.Intent.OnSymptomConfirmed -> processSetupSymptom(intent.isFront, intent.isHighSpeed)
        }
    }

    private fun updateSelectedBike(bikeId: Int) =
        viewModelScope.launch {
            bikes.value.getOrNull(bikeId)?.let {
                selectedBike.emit(it)
                emitDefaultState()
            }
        }

    private fun toggleSelectedSymptom(symptom: SetupSymptom) {
        (_state.value as? SetupWizardContract.State.SelectSymptom)?.let {
            viewModelScope.launch {
                _state.emit(
                    SetupWizardContract.State.SelectSymptom(
                        it.symptoms
                            .map {
                                if (it.setupSymptom == symptom) {
                                    it.copy(
                                        selected = !it.selected
                                    )
                                } else {
                                    if (it.selected) {
                                        it.copy(selected = false)
                                    } else {
                                        it
                                    }
                                }
                            }.toImmutableList(),
                        it.bikes
                    )
                )
            }
        }
    }

    private fun processSetupSymptom(
        isFront: Boolean,
        isHighSpeed: Boolean
    ) = iOScope.launch {
        _event.emit(SetupWizardContract.Event.HideSymptomBottomSheet)

        val selectedSymptom =
            (_state.value as? SetupWizardContract.State.SelectSymptom)?.symptoms?.firstOrNull { it.selected } ?: return@launch
        val bike = selectedBike.value ?: return@launch
        val front = if (selectedSymptom.setupSymptom.requiresLocation) isFront else null
        val highSpeed = if (selectedSymptom.setupSymptom.requiresSpeed) isHighSpeed else null

        getSetupSolutionUseCase(selectedSymptom.setupSymptom, bike, front, highSpeed)
        emitDefaultState()
    }

    private fun emitSelectSymptomState() =
        viewModelScope.launch {
            SetupSymptom.entries.map { setupSymptomViewMapper.map(it) }.also {
                _state.emit(SetupWizardContract.State.SelectSymptom(it.toImmutableList(), bikes.value))
            }
            _event.emit(SetupWizardContract.Event.ShowSymptomBottomSheet)
        }

    private suspend fun emitDefaultState() {
        val selectedBike = selectedBike.value
        setupRecommendations =
            setupRecommendationRepository.getSetupRecommendations(selectedBike?.id ?: -1).first().filter {
                it.isAccepted == null
            }
        _state.emit(
            if (setupRecommendations.isNotEmpty()) {
                SetupWizardContract.State.Recommendation(
                    setupRecommendationViewMapper.map(setupRecommendations.first()).toImmutableList(),
                    bikes.value
                )
            } else {
                SetupWizardContract.State.Start(bikes.value)
            }
        )
    }
}

interface SetupWizardContract :
    UnidirectionalViewModel<SetupWizardContract.State, SetupWizardContract.Intent, SetupWizardContract.Event> {
    sealed class State(
        val bikes: ImmutableList<Bike?> = persistentListOf()
    ) {
        class Start(
            bikes: ImmutableList<Bike?>
        ) : State(bikes)

        class SelectSymptom(
            val symptoms: ImmutableList<SetupSymptomView>,
            bikes: ImmutableList<Bike?>
        ) : State(bikes)

        class Recommendation(
            val setupRecommendation: ImmutableList<SetupRecommendationView>,
            bikes: ImmutableList<Bike?>
        ) : State(bikes)

        class Success(
            bikes: ImmutableList<Bike?>
        ) : State(bikes)
    }

    data class SetupRecommendationView(
        val value: Double,
        val target1: StringResource,
        val target2: StringResource,
        val sentence: StringResource,
        val unit: StringResource?
    )

    sealed class Event {
        object Error : Event()

        object ShowSymptomBottomSheet : Event()

        object HideSymptomBottomSheet : Event()
    }

    sealed class Intent {
        class OnSelectedBikeChanged(
            val index: Int
        ) : Intent()

        object OnStartTroubleshootClick : Intent()

        object OnSeePreviousRecommendationsClick : Intent()

        class OnSymptomSelected(
            val symptom: SetupSymptom
        ) : Intent()

        class OnSymptomConfirmed(
            val isFront: Boolean,
            val isHighSpeed: Boolean
        ) : Intent()

        object OnBottomSheetDismissed : Intent()

        object OnRecommendationAccepted : Intent()

        object OnRecommendationDeclined : Intent()
    }
}