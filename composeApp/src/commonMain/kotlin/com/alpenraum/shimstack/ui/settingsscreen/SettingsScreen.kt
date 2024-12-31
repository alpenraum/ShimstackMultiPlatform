package com.alpenraum.shimstack.ui.settingsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alpenraum.shimstack.base.use
import com.alpenraum.shimstack.ui.base.compose.components.AttachToLifeCycle
import com.alpenraum.shimstack.ui.base.compose.components.MultiOptionToggle
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.imperial
import shimstackmultiplatform.composeapp.generated.resources.metric
import shimstackmultiplatform.composeapp.generated.resources.settings_measurement_unit_type

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    AttachToLifeCycle(viewModel = viewModel)
    val (state, intents, _) = use(viewModel = viewModel, navController)
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp).verticalScroll(rememberScrollState())
    ) {
        state.settings.forEach { setting ->
            when (setting) {
                is SettingsContract.Settings.PreferredTheme ->
                    SettingsMultiSwitch(
                        setting.options,
                        setting.selectedIndex
                    ) { intents(SettingsContract.Intent.OnPreferredThemeChange(it)) }

                is SettingsContract.Settings.AllowAnalytics ->
                    SettingsToggleRow(setting.label, setting.setting) {
                        intents(
                            SettingsContract.Intent.OnAllowAnalyticsChange(it)
                        )
                    }

                is SettingsContract.Settings.MeasurementUnit ->
                    SettingsMultiSwitch(
                        setting.options,
                        setting.selectedIndex
                    ) { intents(SettingsContract.Intent.OnMeasurementUnitTypeChange(it)) }
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    label: StringResource,
    setting: Boolean,
    modifier: Modifier = Modifier,
    onDataChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(label),
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            modifier = Modifier.weight(1.0f)
        )
        Switch(
            checked = setting,
            onCheckedChange = onDataChange
        )
    }
}

@Composable
private fun SettingsMultiSwitch(
    options: List<StringResource>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onOptionSelect: (Int) -> Unit
) {
    Column(modifier.padding(8.dp)) {
        Text(stringResource(Res.string.settings_measurement_unit_type), modifier = Modifier.padding(bottom = 4.dp))
        MultiOptionToggle(options, selectedIndex, onOptionSelect = onOptionSelect)
    }
}

@Preview
@Composable
private fun SettingPreview() {
    AppTheme {
        SettingsToggleRow(
            SettingsContract.Settings.AllowAnalytics(false).label,
            false
        ) {}
    }
}

@Preview
@Composable
private fun MultiSettingPreview() {
    AppTheme {
        MultiOptionToggle(
            listOf(Res.string.metric, Res.string.imperial),
            selectedIndex = 0
        ) { }
    }
}