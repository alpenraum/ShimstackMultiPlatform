package com.alpenraum.shimstack.ui.newBike.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alpenraum.shimstack.domain.model.bike.BikeType
import com.alpenraum.shimstack.domain.model.biketemplate.BikeTemplate
import com.alpenraum.shimstack.ui.base.compose.components.ButtonText
import com.alpenraum.shimstack.ui.base.compose.components.InfoText
import com.alpenraum.shimstack.ui.base.compose.components.LargeButton
import com.alpenraum.shimstack.ui.base.compose.components.TextInput
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.newBike.NewBikeContract
import com.alpenraum.shimstack.ui.newBike.NewBikeDestinations
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.copy_new_bike_entry
import shimstackmultiplatform.composeapp.generated.resources.label_new_bike_search
import shimstackmultiplatform.composeapp.generated.resources.label_new_bike_travel
import shimstackmultiplatform.composeapp.generated.resources.label_next_step

@Composable
fun EntryScreen(
    navigator: NavController? = null,
    state: NewBikeContract.State,
    intent: (NewBikeContract.Intent) -> Unit,
    event: SharedFlow<NewBikeContract.Event>
) {
    LaunchedEffect(key1 = Unit) {
        event.collectLatest {
            when (it) {
                NewBikeContract.Event.NavigateToNextStep ->
                    navigator?.navigate(
                        NewBikeDestinations.ENTER_DETAILS.route
                    )

                else -> {}
            }
        }
    }
    Column {
        Text(
            text = stringResource(Res.string.copy_new_bike_entry),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        var userInput by rememberSaveable { mutableStateOf("") }
        TextInput(
            value = userInput,
            onValueChange = {
                userInput = it
                intent(NewBikeContract.Intent.Filter(it))
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            label = stringResource(Res.string.label_new_bike_search)
        )
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = state.bikeTemplates.isNotEmpty(),
            label = "",
            transitionSpec = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) togetherWith fadeOut() +
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )
            }
        ) {
            if (it) {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1.0f, fill = false)
                            .padding(vertical = 16.dp)
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        itemsIndexed(state.bikeTemplates) { index, item ->
                            ListItem(bike = item, intent)
                            if (index < state.bikeTemplates.lastIndex) HorizontalDivider()
                        }
                    }
                }
            } else {
                LargeButton(onClick = {
                    intent(NewBikeContract.Intent.OnNextClicked())
                }, modifier = Modifier.padding(vertical = 16.dp)) {
                    ButtonText(Res.string.label_next_step)
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    bike: BikeTemplate,
    intent: (NewBikeContract.Intent) -> Unit
) {
    Row(
        modifier =
            Modifier
                .clickable { intent(NewBikeContract.Intent.BikeTemplateSelected(bike)) }
                .padding(
                    vertical = 8.dp
                ).padding(horizontal = 16.dp)
                .semantics(true) {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Column {
            Text(text = bike.name)
            InfoText(textRes = bike.type.labelRes)
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Column {
            Text(
                text =
                    stringResource(
                        Res.string.label_new_bike_travel,
                        bike.frontSuspensionTravelInMM,
                        bike.rearSuspensionTravelInMM
                    ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun ListItemPreview() {
    AppTheme {
        ListItem(bike = BikeTemplate.testData()) {}
    }
}

@Preview
@Composable
private fun EntryPreview() {
    AppTheme {
        val templates =
            buildList {
                for (i in 0 until 30) {
                    add(
                        BikeTemplate(
                            id = i,
                            name = "bike $i",
                            type = BikeType.ENDURO,
                            false,
                            150,
                            130,
                            0.0,
                            0.0,
                            0.0,
                            0.0
                        )
                    )
                }
            }.toImmutableList()
        EntryScreen(
            state = NewBikeContract.State(templates),
            intent = {},
            event = MutableSharedFlow()
        )
    }
}