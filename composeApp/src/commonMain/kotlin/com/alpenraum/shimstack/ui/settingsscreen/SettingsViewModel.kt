package com.alpenraum.shimstack.ui.settingsscreen

import androidx.navigation.NavController
import com.alpenraum.shimstack.base.BaseViewModel
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.base.UnidirectionalViewModel
import com.alpenraum.shimstack.domain.model.PreferredTheme
import com.alpenraum.shimstack.domain.model.measurementunit.MeasurementUnitType
import com.alpenraum.shimstack.domain.userSettings.GetUserSettingsUseCase
import com.alpenraum.shimstack.domain.userSettings.UserSettings
import com.alpenraum.shimstack.domain.userSettings.UserSettingsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.koin.android.annotation.KoinViewModel
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.imperial
import shimstackmultiplatform.composeapp.generated.resources.label_auto_theme
import shimstackmultiplatform.composeapp.generated.resources.label_dark_theme
import shimstackmultiplatform.composeapp.generated.resources.label_light_theme
import shimstackmultiplatform.composeapp.generated.resources.metric
import shimstackmultiplatform.composeapp.generated.resources.settings_allow_analytics
import shimstackmultiplatform.composeapp.generated.resources.settings_measurement_unit_type
import shimstackmultiplatform.composeapp.generated.resources.settings_preferred_theme

@KoinViewModel
class SettingsViewModel(
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val userSettingsRepository: UserSettingsRepository,
    dispatchersProvider: DispatchersProvider
) : BaseViewModel(dispatchersProvider),
    SettingsContract {
    private val _event = MutableSharedFlow<SettingsContract.Event>()
    override val state: StateFlow<SettingsContract.State> =
        getUserSettingsUseCase()
            .map(::mapUserSettings)
            .map(::createState)
            .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = SettingsContract.State())
    override val event: SharedFlow<SettingsContract.Event>
        get() = _event.asSharedFlow()

    override fun intent(
        intent: SettingsContract.Intent,
        navController: NavController
    ) {
        when (intent) {
            is SettingsContract.Intent.OnAllowAnalyticsChange ->
                iOScope.launch {
                    userSettingsRepository.updateIsAnalyticsEnabled(
                        intent.newSetting
                    )
                }

            is SettingsContract.Intent.OnMeasurementUnitTypeChange ->
                iOScope.launch {
                    userSettingsRepository.updateMeasurementUnitType(
                        MeasurementUnitType.entries[intent.newSettingIndex]
                    )
                }

            is SettingsContract.Intent.OnPreferredThemeChange ->
                iOScope.launch {
                    userSettingsRepository.updatePreferredThemeEnabled(
                        PreferredTheme.entries[intent.newSettingIndex]
                    )
                }
        }
    }

    private fun mapUserSettings(userSetting: UserSettings): List<SettingsContract.Settings> =
        buildList {
            add(SettingsContract.Settings.AllowAnalytics(userSetting.isAnalyticsEnabled))
            add(
                SettingsContract.Settings.MeasurementUnit(
                    listOf(
                        Res.string.metric,
                        Res.string.imperial
                    ),
                    MeasurementUnitType.entries.indexOf(userSetting.measurementUnitType)
                )
            )
            add(
                SettingsContract.Settings.PreferredTheme(
                    listOf(
                        Res.string.label_light_theme,
                        Res.string.label_dark_theme,
                        Res.string.label_auto_theme
                    ),
                    PreferredTheme.entries.indexOf(userSetting.preferredTheme)
                )
            )
        }

    private fun createState(list: List<SettingsContract.Settings>): SettingsContract.State = SettingsContract.State(list)
}

interface SettingsContract : UnidirectionalViewModel<SettingsContract.State, SettingsContract.Intent, SettingsContract.Event> {
    data class State(
        val settings: List<Settings> = emptyList()
    )

    sealed class Event

    sealed class Intent {
        class OnPreferredThemeChange(
            val newSettingIndex: Int
        ) : Intent()

        class OnAllowAnalyticsChange(
            val newSetting: Boolean
        ) : Intent()

        class OnMeasurementUnitTypeChange(
            val newSettingIndex: Int
        ) : Intent()
    }

    sealed class Settings(
        val label: StringResource
    ) {
        class PreferredTheme(
            val options: List<StringResource>,
            val selectedIndex: Int
        ) : Settings(Res.string.settings_preferred_theme)

        class AllowAnalytics(
            val setting: Boolean
        ) : Settings(Res.string.settings_allow_analytics)

        class MeasurementUnit(
            val options: List<StringResource>,
            val selectedIndex: Int
        ) : Settings(Res.string.settings_measurement_unit_type)
    }
}