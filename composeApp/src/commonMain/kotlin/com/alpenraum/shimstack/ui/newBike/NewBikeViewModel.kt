package com.alpenraum.shimstack.ui.newBike

import androidx.compose.runtime.Immutable
import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.base.UnidirectionalViewModel
import com.alpenraum.shimstack.data.bike.LocalBikeRepository
import com.alpenraum.shimstack.data.biketemplate.LocalBikeTemplateRepository
import com.alpenraum.shimstack.domain.bikeservice.ValidateBikeUseCase
import com.alpenraum.shimstack.domain.bikeservice.ValidateSetupUseCase
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.model.bike.BikeType
import com.alpenraum.shimstack.domain.model.bikesetup.DetailsInputData
import com.alpenraum.shimstack.domain.model.bikesetup.SetupInputData
import com.alpenraum.shimstack.domain.model.biketemplate.BikeTemplate
import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.model.suspension.Damping
import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.model.tire.Tire
import com.alpenraum.shimstack.domain.userSettings.GetUserSettingsUseCase
import com.alpenraum.shimstack.model.measurementunit.MeasurementUnitType
import com.alpenraum.shimstack.ui.newBike.navigation.NewBikeNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.error_create_bike
import kotlin.time.Duration.Companion.milliseconds

// FIXME: Really unhappy with the state management in this, needs to be done differently. Needs to be split up into separate screens and VMs
class NewBikeViewModel(
    private val bikeTemplateRepository: LocalBikeTemplateRepository,
    private val bikeRepository: LocalBikeRepository,
    private val validateBikeUseCase: ValidateBikeUseCase,
    private val validateSetupUseCase: ValidateSetupUseCase,
    userSettingsUseCase: GetUserSettingsUseCase,
    private val newBikeNavigator: NewBikeNavigator,
    dispatchersProvider: DispatchersProvider
) : BaseViewModel(dispatchersProvider),
    NewBikeContract {
    private val _state = MutableStateFlow(NewBikeContract.State())
    override val state: StateFlow<NewBikeContract.State>
        get() = _state.asStateFlow()

    private val filterFlow = MutableStateFlow("")
    private var filterJob: Job? = null

    private val _event = MutableSharedFlow<NewBikeContract.Event>()
    override val event: SharedFlow<NewBikeContract.Event>
        get() = _event.asSharedFlow()

    private var measurementUnitType = MeasurementUnitType.METRIC

    init {
        iOScope.launch {
            userSettingsUseCase().collectLatest { measurementUnitType = it.measurementUnitType }
        }
        iOScope.launch {
            _state.emit(
                NewBikeContract.State(
                    bikeTemplateRepository
                        .getBikeTemplatesFilteredByName(
                            ""
                        ).toImmutableList(),
                    measurementUnitType = measurementUnitType
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        iOScope.launch {
            filterJob = launchFilterBikeTemplates()
        }
    }

    override fun onStop() {
        super.onStop()
        filterJob?.cancel()
    }

    override fun intent(
        intent: NewBikeContract.Intent,
        navController: NavController
    ) {
        iOScope.launch {
            when (intent) {
                is NewBikeContract.Intent.Filter -> {
                    filterFlow.emit(intent.filter)
                }

                NewBikeContract.Intent.OnFlowFinished -> {
                    _event.emit(NewBikeContract.Event.NavigateToHomeScreen)
                }

// Details Data Input
                is NewBikeContract.Intent.BikeTemplateSelected ->
                    goToEnterDetailsScreen(
                        intent.bike
                    )

                is NewBikeContract.Intent.BikeNameInput ->
                    validateAndUpdateInput(
                        DetailsInputData(name = intent.name)
                    )

                is NewBikeContract.Intent.BikeTypeInput ->
                    validateAndUpdateInput(
                        bikeType = intent.type
                    )

                is NewBikeContract.Intent.EbikeInput ->
                    validateAndUpdateInput(
                        isEbike = intent.isEbike
                    )

                is NewBikeContract.Intent.FrontInternalRimWidthInput -> {
                    validateAndUpdateInput(
                        DetailsInputData(frontInternalRimWidth = intent.width)
                    )
                }

                is NewBikeContract.Intent.FrontSuspensionInput -> {
                    validateAndUpdateInput(
                        DetailsInputData(intent.travel)
                    )
                }

                is NewBikeContract.Intent.FrontTireWidthInput -> {
                    validateAndUpdateInput(
                        DetailsInputData(frontTireWidth = intent.width)
                    )
                }

                is NewBikeContract.Intent.RearInternalRimWidthInput -> {
                    validateAndUpdateInput(
                        DetailsInputData(rearInternalRimWidth = intent.width)
                    )
                }

                is NewBikeContract.Intent.RearSuspensionInput -> {
                    validateAndUpdateInput(
                        DetailsInputData(intent.travel)
                    )
                }

                is NewBikeContract.Intent.RearTireWidthInput -> {
                    validateAndUpdateInput(
                        DetailsInputData(rearTireWidth = intent.width)
                    )
                }

                is NewBikeContract.Intent.OnNextClicked -> {
                    if (intent.flowFinished) {
                        try {
                            saveBike()
                            _event.emit(
                                NewBikeContract.Event.NavigateToNextStep
                            )
                        } catch (e: Exception) {
                            _event.emit(NewBikeContract.Event.ShowToast(Res.string.error_create_bike))
                        }
                    } else {
                        _event.emit(
                            NewBikeContract.Event.NavigateToNextStep
                        )
                    }
                }

                is NewBikeContract.Intent.FrontTirePressureInput ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(frontTirePressure = intent.pressure)
                    )

                is NewBikeContract.Intent.RearTirePressureInput ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(rearTirePressure = intent.pressure)
                    )

                is NewBikeContract.Intent.HSCInput -> {
                    if (intent.isFork) {
                        validateAndUpdateInput(hasHSCFork = intent.hasHsc)
                    } else {
                        validateAndUpdateInput(hasHSCShock = intent.hasHsc)
                    }
                }

                is NewBikeContract.Intent.HSRInput -> {
                    if (intent.isFork) {
                        validateAndUpdateInput(hasHSRFork = intent.hasHsr)
                    } else {
                        validateAndUpdateInput(hasHSRShock = intent.hasHsr)
                    }
                }

                is NewBikeContract.Intent.FrontSuspensionPressure ->
                    validateAndUpdateInput(
                        setupInputData =
                            SetupInputData(
                                frontSuspensionPressure = intent.pressure
                            )
                    )

                is NewBikeContract.Intent.FrontSuspensionTokens ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(frontSuspensionTokens = intent.tokens)
                    )

                is NewBikeContract.Intent.RearSuspensionPressure ->
                    validateAndUpdateInput(
                        setupInputData =
                            SetupInputData(
                                rearSuspensionPressure = intent.pressure
                            )
                    )

                is NewBikeContract.Intent.RearSuspensionTokens ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(rearSuspensionTokens = intent.tokens)
                    )

                is NewBikeContract.Intent.FrontSuspensionHSC ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(frontSuspensionHSC = intent.clicks)
                    )

                is NewBikeContract.Intent.FrontSuspensionHSR ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(frontSuspensionHSR = intent.clicks)
                    )

                is NewBikeContract.Intent.FrontSuspensionLSC ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(frontSuspensionLSC = intent.clicks)
                    )

                is NewBikeContract.Intent.FrontSuspensionLSR ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(frontSuspensionLSR = intent.clicks)
                    )

                is NewBikeContract.Intent.RearSuspensionHSC ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(rearSuspensionHSC = intent.clicks)
                    )

                is NewBikeContract.Intent.RearSuspensionHSR ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(rearSuspensionHSR = intent.clicks)
                    )

                is NewBikeContract.Intent.RearSuspensionLSC ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(rearSuspensionLSC = intent.clicks)
                    )

                is NewBikeContract.Intent.RearSuspensionLSR ->
                    validateAndUpdateInput(
                        setupInputData = SetupInputData(rearSuspensionLSR = intent.clicks)
                    )
            }
        }
    }

    private suspend fun saveBike() {
        bikeRepository.createBike(_state.value.toBike(measurementUnitType))
    }

    private suspend fun validateAndUpdateInput(
        detailsInputData: DetailsInputData? = null,
        setupInputData: SetupInputData? = null,
        isEbike: Boolean? = null,
        bikeType: BikeType? = null,
        hasHSCFork: Boolean? = null,
        hasHSRFork: Boolean? = null,
        hasHSCShock: Boolean? = null,
        hasHSRShock: Boolean? = null
    ) {
        iOScope.launch {
            val detailsInput = detailsInputData?.let { createNewInputData(it) }
            val setupInput = setupInputData?.let { createNewInputSetup(it) }
            val detailsValidationResult =
                detailsInput?.let {
                    validateBikeUseCase(
                        it,
                        bikeType ?: state.value.bikeType
                    )
                }
            val setupValidationResult = setupInput?.let { validateSetupUseCase(it) }
            _state.emit(
                state.value.copy(
                    detailsInput = detailsInput ?: state.value.detailsInput,
                    setupInput = setupInput ?: state.value.setupInput,
                    isEbike = isEbike ?: state.value.isEbike,
                    bikeType = bikeType ?: state.value.bikeType,
                    hasHSCFork = hasHSCFork ?: state.value.hasHSCFork,
                    hasHSRFork = hasHSRFork ?: state.value.hasHSRFork,
                    hasHSCShock = hasHSCShock ?: state.value.hasHSCShock,
                    hasHSRShock = hasHSRShock ?: state.value.hasHSRShock,
                    detailsValidationErrors = detailsValidationResult?.getOrNull(),
                    setupValidationErrors =
                        if (setupValidationResult?.isSuccess?.not() == true) {
                            setupValidationResult.getOrNull()
                        } else {
                            null
                        },
                    showSetupOutlierHint =
                        setupValidationResult?.getOrElse { ValidateSetupUseCase.SubResult.SUCCESS } ==
                            ValidateSetupUseCase.SubResult.OUTLIER
                )
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun launchFilterBikeTemplates() =
        iOScope.launch {
            filterFlow.sample(200.milliseconds).distinctUntilChanged().collectLatest {
                val filteredTemplates =
                    bikeTemplateRepository.getBikeTemplatesFilteredByName(
                        it
                    )
                _state.emit(
                    state.value.copy(bikeTemplates = filteredTemplates.toImmutableList())
                )
            }
        }

    private fun goToEnterDetailsScreen(template: BikeTemplate?) =
        iOScope.launch {
            validateAndUpdateInput(
                detailsInputData = mapFromBike(template?.toBike() ?: Bike.empty()),
                isEbike = template?.isEBike,
                bikeType = template?.type
            )
            _event.emit(NewBikeContract.Event.NavigateToNextStep)
        }

    private fun createNewInputData(detailsInputData: DetailsInputData) =
        DetailsInputData(
            detailsInputData.name ?: state.value.detailsInput.name,
            detailsInputData.frontTravel ?: state.value.detailsInput.frontTravel,
            detailsInputData.rearTravel ?: state.value.detailsInput.rearTravel,
            detailsInputData.frontTireWidth ?: state.value.detailsInput.frontTireWidth,
            detailsInputData.rearTireWidth ?: state.value.detailsInput.rearTireWidth,
            detailsInputData.frontInternalRimWidth ?: state.value.detailsInput.frontInternalRimWidth,
            detailsInputData.rearInternalRimWidth ?: state.value.detailsInput.rearInternalRimWidth
        )

    private fun createNewInputSetup(setupInput: SetupInputData) =
        SetupInputData(
            setupInput.frontTirePressure ?: state.value.setupInput.frontTirePressure,
            setupInput.rearTirePressure ?: state.value.setupInput.rearTirePressure,
            frontSuspensionPressure = setupInput.frontSuspensionPressure ?: state.value.setupInput.frontSuspensionPressure,
            rearSuspensionPressure = setupInput.rearSuspensionPressure ?: state.value.setupInput.rearSuspensionPressure,
            frontSuspensionTokens = setupInput.frontSuspensionTokens ?: state.value.setupInput.frontSuspensionTokens,
            rearSuspensionTokens = setupInput.rearSuspensionTokens ?: state.value.setupInput.rearSuspensionTokens,
            frontSuspensionLSC = setupInput.frontSuspensionLSC ?: state.value.setupInput.frontSuspensionLSC,
            frontSuspensionLSR = setupInput.frontSuspensionLSR ?: state.value.setupInput.frontSuspensionLSR,
            frontSuspensionHSC = setupInput.frontSuspensionHSC ?: state.value.setupInput.frontSuspensionHSC,
            frontSuspensionHSR = setupInput.frontSuspensionHSR ?: state.value.setupInput.frontSuspensionHSR,
            rearSuspensionLSC = setupInput.rearSuspensionLSC ?: state.value.setupInput.rearSuspensionLSC,
            rearSuspensionLSR = setupInput.rearSuspensionLSR ?: state.value.setupInput.rearSuspensionLSR,
            rearSuspensionHSC = setupInput.rearSuspensionHSC ?: state.value.setupInput.rearSuspensionHSC,
            rearSuspensionHSR = setupInput.rearSuspensionHSR ?: state.value.setupInput.rearSuspensionHSR
        )

    private fun mapFromBike(bike: Bike) =
        DetailsInputData(
            bike.name,
            bike.frontSuspension
                ?.travel
                ?.getAsUnit(measurementUnitType)
                ?.toString(),
            bike.frontSuspension
                ?.travel
                ?.getAsUnit(measurementUnitType)
                ?.toString(),
            bike.frontTire.width
                .getAsUnit(measurementUnitType)
                .toString(),
            bike.rearTire.width
                .getAsUnit(measurementUnitType)
                .toString(),
            bike.frontTire.internalRimWidthInMM?.toString(),
            bike.rearTire.internalRimWidthInMM?.toString()
        )
}

interface NewBikeContract : UnidirectionalViewModel<NewBikeContract.State, NewBikeContract.Intent, NewBikeContract.Event> {
    @Immutable
    data class State(
        val bikeTemplates: ImmutableList<BikeTemplate> = persistentListOf(),
        val detailsValidationErrors: ValidateBikeUseCase.DetailsFailure? = null,
        val setupValidationErrors: ValidateSetupUseCase.SubResult? = null,
        val showSetupOutlierHint: Boolean = false,
        val isEbike: Boolean = false,
        val bikeType: BikeType = BikeType.UNKNOWN,
        val detailsInput: DetailsInputData = DetailsInputData(),
        val setupInput: SetupInputData = SetupInputData(),
        val hasHSCFork: Boolean = false,
        val hasHSRFork: Boolean = false,
        val hasHSCShock: Boolean = false,
        val hasHSRShock: Boolean = false,
        val measurementUnitType: MeasurementUnitType = MeasurementUnitType.METRIC
    ) {
        fun hasFrontSuspension() = detailsInput.frontTravel?.isNotEmpty() == true

        fun hasRearSuspension() = detailsInput.rearTravel?.isNotEmpty() == true

        fun toBike(measurementUnitType: MeasurementUnitType): Bike {
            val frontSuspension =
                if (hasFrontSuspension()) {
                    Suspension(
                        with(
                            setupInput.frontSuspensionPressure?.toDouble() ?: 0.0
                        ) { if (measurementUnitType.isMetric()) Pressure(this) else Pressure.fromImperial(this) },
                        Damping(
                            setupInput.frontSuspensionLSC?.toInt() ?: 0,
                            if (hasHSCFork) setupInput.frontSuspensionHSC?.toInt() ?: 0 else null
                        ),
                        Damping(
                            setupInput.frontSuspensionLSR?.toInt() ?: 0,
                            if (hasHSRFork) setupInput.frontSuspensionHSR?.toInt() ?: 0 else null
                        ),
                        setupInput.frontSuspensionTokens?.toInt() ?: 0,
                        with(
                            detailsInput.frontTravel?.toDouble() ?: 0.0
                        ) { if (measurementUnitType.isMetric()) Distance(this) else Distance.fromImperial(this) }
                    )
                } else {
                    null
                }
            val rearSuspension =
                if (hasRearSuspension()) {
                    Suspension(
                        with(
                            setupInput.rearSuspensionPressure?.toDouble() ?: 0.0
                        ) { if (measurementUnitType.isMetric()) Pressure(this) else Pressure.fromImperial(this) },
                        Damping(
                            setupInput.rearSuspensionLSC?.toInt() ?: 0,
                            if (hasHSCShock) setupInput.rearSuspensionHSC?.toInt() ?: 0 else null
                        ),
                        Damping(
                            setupInput.rearSuspensionLSR?.toInt() ?: 0,
                            if (hasHSRShock) setupInput.rearSuspensionHSR?.toInt() ?: 0 else null
                        ),
                        setupInput.rearSuspensionTokens?.toInt() ?: 0,
                        with(
                            detailsInput.rearTravel?.toDouble() ?: 0.0
                        ) { if (measurementUnitType.isMetric()) Distance(this) else Distance.fromImperial(this) }
                    )
                } else {
                    null
                }
            val frontTire =
                Tire(
                    with(
                        setupInput.frontTirePressure?.toDouble() ?: 0.0
                    ) { if (measurementUnitType.isMetric()) Pressure(this) else Pressure.fromImperial(this) },
                    with(
                        detailsInput.frontTireWidth?.toDouble() ?: 0.0
                    ) { if (measurementUnitType.isMetric()) Distance(this) else Distance.fromImperial(this) },
                    detailsInput.frontInternalRimWidth?.toDoubleOrNull()?.let { Distance(it) }
                )
            val rearTire =
                Tire(
                    with(
                        setupInput.rearTirePressure?.toDouble() ?: 0.0
                    ) { if (measurementUnitType.isMetric()) Pressure(this) else Pressure.fromImperial(this) },
                    with(
                        detailsInput.rearTireWidth?.toDouble() ?: 0.0
                    ) { if (measurementUnitType.isMetric()) Distance(this) else Distance.fromImperial(this) },
                    detailsInput.rearInternalRimWidth?.toDoubleOrNull()?.let { Distance(it) }
                )

            return Bike(
                name = detailsInput.name ?: "",
                type = bikeType,
                isEBike = isEbike,
                frontSuspension = frontSuspension,
                rearSuspension = rearSuspension,
                frontTire = frontTire,
                rearTire = rearTire
            )
        }
    }

    sealed class Event {
        data object NavigateToNextStep : Event()

        data object NavigateToPreviousStep : Event()

        data object NavigateToHomeScreen : Event()

        class ShowToast(
            val messageRes: StringResource
        ) : Event()
    }

    sealed class Intent {
        class Filter(
            val filter: String
        ) : Intent()

        class BikeTemplateSelected(
            val bike: BikeTemplate
        ) : Intent()

        class OnNextClicked(
            val flowFinished: Boolean = false
        ) : Intent()

        sealed class DataInput : Intent()

        class BikeNameInput(
            val name: String
        ) : DataInput()

        class BikeTypeInput(
            val type: BikeType
        ) : DataInput()

        class EbikeInput(
            val isEbike: Boolean
        ) : DataInput()

        class HSCInput(
            val hasHsc: Boolean,
            val isFork: Boolean
        ) : DataInput()

        class HSRInput(
            val hasHsr: Boolean,
            val isFork: Boolean
        ) : DataInput()

        class FrontSuspensionInput(
            val travel: String?
        ) : DataInput()

        class RearSuspensionInput(
            val travel: String?
        ) : DataInput()

        class FrontTireWidthInput(
            val width: String
        ) : DataInput()

        class RearTireWidthInput(
            val width: String
        ) : DataInput()

        class FrontInternalRimWidthInput(
            val width: String?
        ) : DataInput()

        class RearInternalRimWidthInput(
            val width: String?
        ) : DataInput()

        sealed class SetupInput : Intent()

        class FrontTirePressureInput(
            val pressure: String?
        ) : SetupInput()

        class RearTirePressureInput(
            val pressure: String?
        ) : SetupInput()

        class FrontSuspensionPressure(
            val pressure: String?
        ) : SetupInput()

        class FrontSuspensionTokens(
            val tokens: String?
        ) : SetupInput()

        class RearSuspensionPressure(
            val pressure: String?
        ) : SetupInput()

        class RearSuspensionTokens(
            val tokens: String?
        ) : SetupInput()

        class FrontSuspensionLSC(
            val clicks: String?
        ) : SetupInput()

        class FrontSuspensionHSC(
            val clicks: String?
        ) : SetupInput()

        class FrontSuspensionLSR(
            val clicks: String?
        ) : SetupInput()

        class FrontSuspensionHSR(
            val clicks: String?
        ) : SetupInput()

        class RearSuspensionLSC(
            val clicks: String?
        ) : SetupInput()

        class RearSuspensionHSC(
            val clicks: String?
        ) : SetupInput()

        class RearSuspensionLSR(
            val clicks: String?
        ) : SetupInput()

        class RearSuspensionHSR(
            val clicks: String?
        ) : SetupInput()

        data object OnFlowFinished : Intent()
    }
}