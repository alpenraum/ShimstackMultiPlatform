package com.alpenraum.shimstack.ui.newBike.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alpenraum.shimstack.domain.model.bikesetup.DetailsInputData
import com.alpenraum.shimstack.ui.base.compose.components.ButtonText
import com.alpenraum.shimstack.ui.base.compose.components.InfoText
import com.alpenraum.shimstack.ui.base.compose.components.LargeButton
import com.alpenraum.shimstack.ui.base.compose.components.TextInput
import com.alpenraum.shimstack.ui.base.compose.number
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.newBike.NewBikeContract
import com.alpenraum.shimstack.ui.newBike.NewBikeDestinations
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.bar
import shimstackmultiplatform.composeapp.generated.resources.comp
import shimstackmultiplatform.composeapp.generated.resources.copy_setup_outlier_hint
import shimstackmultiplatform.composeapp.generated.resources.front
import shimstackmultiplatform.composeapp.generated.resources.info_compression
import shimstackmultiplatform.composeapp.generated.resources.info_rebound
import shimstackmultiplatform.composeapp.generated.resources.label_entersetup_high_speed_clicks
import shimstackmultiplatform.composeapp.generated.resources.label_entersetup_intro
import shimstackmultiplatform.composeapp.generated.resources.label_entersetup_low_speed_clicks
import shimstackmultiplatform.composeapp.generated.resources.label_front_suspension
import shimstackmultiplatform.composeapp.generated.resources.label_next_step
import shimstackmultiplatform.composeapp.generated.resources.label_rear_suspension
import shimstackmultiplatform.composeapp.generated.resources.label_tire_pressure
import shimstackmultiplatform.composeapp.generated.resources.pressure
import shimstackmultiplatform.composeapp.generated.resources.rear
import shimstackmultiplatform.composeapp.generated.resources.rebound
import shimstackmultiplatform.composeapp.generated.resources.sag
import shimstackmultiplatform.composeapp.generated.resources.tokens

@Composable
fun EnterSetupScreen(
    state: NewBikeContract.State,
    intent: (NewBikeContract.Intent) -> Unit,
    event: SharedFlow<NewBikeContract.Event>,
    navigator: NavController?
) {
    LaunchedEffect(key1 = Unit) {
        event.collectLatest {
            when (it) {
                NewBikeContract.Event.NavigateToNextStep -> {
                    navigator?.navigate(NewBikeDestinations.SUCCESS.route)
                }

                else -> {}
            }
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = stringResource(Res.string.label_entersetup_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(Res.string.label_tire_pressure),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
            TextInput(
                value = state.setupInput.frontTirePressure ?: "",
                onValueChange = {
                    intent(NewBikeContract.Intent.FrontTirePressureInput(it))
                },
                suffix = stringResource(Res.string.bar),
                modifier =
                    Modifier
                        .weight(1.0f)
                        .padding(top = 8.dp),
                label = "${stringResource(Res.string.front)} ${
                    stringResource(
                        Res.string.label_tire_pressure
                    )
                }",
                keyboardOptions = KeyboardOptions.number(ImeAction.Next)
            )
            TextInput(
                value = state.setupInput.rearTirePressure ?: "",
                onValueChange = {
                    intent(NewBikeContract.Intent.RearTirePressureInput(it))
                },
                suffix = stringResource(Res.string.bar),
                modifier =
                    Modifier
                        .weight(1.0f)
                        .padding(top = 8.dp),
                label = "${stringResource(Res.string.rear)} ${
                    stringResource(
                        Res.string.label_tire_pressure
                    )
                }",
                keyboardOptions = KeyboardOptions.number(ImeAction.Next)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.hasFrontSuspension()) {
            SuspensionInput(
                title = stringResource(Res.string.label_front_suspension),
                pressureInput = state.setupInput.frontSuspensionPressure ?: "",
                sagInput = state.setupInput.frontSag ?: "",
                tokensInput = state.setupInput.frontSuspensionTokens ?: "",
                lscInput = state.setupInput.frontSuspensionLSC ?: "",
                hscInput = state.setupInput.frontSuspensionHSC ?: "",
                lsrInput = state.setupInput.frontSuspensionLSR ?: "",
                hsrInput = state.setupInput.frontSuspensionHSR ?: "",
                showHSC = state.hasHSCFork,
                showHSR = state.hasHSRFork,
                onPressureChanged = { intent(NewBikeContract.Intent.FrontSuspensionPressure(it)) },
                onSagChanged = { intent(NewBikeContract.Intent.FrontSuspensionSag(it)) },
                onTokensChanged = { intent(NewBikeContract.Intent.FrontSuspensionTokens(it)) },
                onLSCChanged = { intent(NewBikeContract.Intent.FrontSuspensionLSC(it)) },
                onHSCChanged = { intent(NewBikeContract.Intent.FrontSuspensionHSC(it)) },
                onLSRChanged = { intent(NewBikeContract.Intent.FrontSuspensionLSR(it)) },
                onHSRChanged = { intent(NewBikeContract.Intent.FrontSuspensionHSR(it)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (state.hasRearSuspension()) {
            SuspensionInput(
                title = stringResource(Res.string.label_rear_suspension),
                pressureInput = state.setupInput.rearSuspensionPressure ?: "",
                sagInput = state.setupInput.rearSag ?: "",
                tokensInput = state.setupInput.rearSuspensionTokens ?: "",
                lscInput = state.setupInput.rearSuspensionLSC ?: "",
                hscInput = state.setupInput.rearSuspensionHSC ?: "",
                lsrInput = state.setupInput.rearSuspensionLSR ?: "",
                hsrInput = state.setupInput.rearSuspensionHSR ?: "",
                showHSC = state.hasHSCShock,
                showHSR = state.hasHSRShock,
                onPressureChanged = { intent(NewBikeContract.Intent.RearSuspensionPressure(it)) },
                onSagChanged = { intent(NewBikeContract.Intent.RearSuspensionSag(it)) },
                onTokensChanged = { intent(NewBikeContract.Intent.RearSuspensionTokens(it)) },
                onLSCChanged = { intent(NewBikeContract.Intent.RearSuspensionLSC(it)) },
                onHSCChanged = { intent(NewBikeContract.Intent.RearSuspensionHSC(it)) },
                onLSRChanged = { intent(NewBikeContract.Intent.RearSuspensionLSR(it)) },
                onHSRChanged = { intent(NewBikeContract.Intent.RearSuspensionHSR(it)) }
            )
        }

        AnimatedVisibility(visible = state.showSetupOutlierHint) {
            InfoText(textRes = Res.string.copy_setup_outlier_hint)
        }
        LargeButton(
            enabled = state.setupValidationErrors == null,
            onClick = {
                intent(NewBikeContract.Intent.OnNextClicked(flowFinished = true))
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            ButtonText(Res.string.label_next_step)
        }
    }
}

@Composable
private fun ColumnScope.SuspensionInput(
    title: String,
    pressureInput: String,
    sagInput: String,
    tokensInput: String,
    lscInput: String,
    hscInput: String?,
    lsrInput: String,
    hsrInput: String?,
    showHSC: Boolean,
    showHSR: Boolean,
    onPressureChanged: (String) -> Unit,
    onSagChanged: (String) -> Unit,
    onTokensChanged: (String) -> Unit,
    onLSCChanged: (String) -> Unit,
    onHSCChanged: (String) -> Unit,
    onLSRChanged: (String) -> Unit,
    onHSRChanged: (String) -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 16.dp)
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
        TextInput(
            value = pressureInput,
            onValueChange = onPressureChanged,
            suffix = stringResource(Res.string.bar),
            label = stringResource(Res.string.pressure),
            keyboardOptions = KeyboardOptions.number(ImeAction.Next),
            modifier = Modifier.weight(1.0f)
        )
        TextInput(
            value = tokensInput,
            onValueChange = onTokensChanged,
            label = stringResource(Res.string.tokens),
            keyboardOptions = KeyboardOptions.number(ImeAction.Next),
            modifier = Modifier.weight(1.0f)
        )
        TextInput(
            value = sagInput,
            onValueChange = onSagChanged,
            label = stringResource(Res.string.sag),
            keyboardOptions = KeyboardOptions.number(ImeAction.Next),
            modifier = Modifier.weight(1.0f)
        )
    }
    Text(
        text = stringResource(Res.string.comp),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 16.dp)
    )
    InfoText(textRes = Res.string.info_compression)
    DampingInput(
        lowSpeed = lscInput,
        highSpeed = hscInput,
        showHighSpeed = showHSC,
        onLowSpeedChanged = onLSCChanged,
        onHighSpeedChanged = onHSCChanged,
        modifier = Modifier.padding(top = 8.dp),
        isLastInput = false
    )
    Text(
        text = stringResource(Res.string.rebound),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 16.dp)
    )
    InfoText(textRes = Res.string.info_rebound)
    DampingInput(
        lowSpeed = lsrInput,
        highSpeed = hsrInput,
        showHighSpeed = showHSR,
        onLowSpeedChanged = onLSRChanged,
        onHighSpeedChanged = onHSRChanged,
        modifier = Modifier.padding(top = 8.dp),
        isLastInput = true
    )
}

@Composable
private fun ColumnScope.DampingInput(
    lowSpeed: String?,
    highSpeed: String?,
    showHighSpeed: Boolean,
    isLastInput: Boolean,
    onHighSpeedChanged: (String) -> Unit,
    onLowSpeedChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        TextInput(
            value = lowSpeed ?: "",
            onValueChange = { onLowSpeedChanged(it) },
            modifier = Modifier.weight(1.0f),
            keyboardOptions =
                KeyboardOptions.number(
                    if (isLastInput) ImeAction.Done else ImeAction.Next
                ),
            label = stringResource(Res.string.label_entersetup_low_speed_clicks)
        )
        AnimatedVisibility(visible = showHighSpeed, modifier = Modifier.weight(1.0f)) {
            TextInput(
                value = highSpeed ?: "",
                onValueChange = { onHighSpeedChanged(it) },
                keyboardOptions = KeyboardOptions.number(ImeAction.Next),
                label = stringResource(Res.string.label_entersetup_high_speed_clicks)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        EnterSetupScreen(
            state =
                NewBikeContract.State(
                    detailsInput =
                        DetailsInputData(
                            frontTravel = "1",
                            rearTravel = "1"
                        ),
                    hasHSCShock = true,
                    hasHSRShock = true,
                    hasHSCFork = true,
                    hasHSRFork = true
                ),
            intent = {},
            event = MutableSharedFlow(),
            null
        )
    }
}