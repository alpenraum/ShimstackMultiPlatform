package com.alpenraum.shimstack.ui.base.compose.components.previews

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.troubleshooting.SetupSymptom
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.setupTroubleshooting.Content
import com.alpenraum.shimstack.ui.setupTroubleshooting.SetupSymptomList
import com.alpenraum.shimstack.ui.setupTroubleshooting.SetupSymptomView
import com.alpenraum.shimstack.ui.setupTroubleshooting.SetupWizardContract
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.clicks
import shimstackmultiplatform.composeapp.generated.resources.copy_new_bike_internal_width_inf
import shimstackmultiplatform.composeapp.generated.resources.front
import shimstackmultiplatform.composeapp.generated.resources.hsc
import shimstackmultiplatform.composeapp.generated.resources.label_tire_pressure
import shimstackmultiplatform.composeapp.generated.resources.setup_wizard_label_recommendation_increase

@ShimstackPreviews
@Composable
fun SetupWizardPreview() =
    AppTheme {
        val state = SetupWizardContract.State.Start(persistentListOf(Bike.empty()))
        Content(state) {}
    }

@Preview
@Composable
fun SetupWizardRecPreview(modifier: Modifier = Modifier) =
    AppTheme {
        val state =
            SetupWizardContract.State.Recommendation(
                persistentListOf(
                    SetupWizardContract.SetupRecommendationView(
                        0.3,
                        Res.string.front,
                        Res.string.hsc,
                        Res.string.setup_wizard_label_recommendation_increase,
                        Res.string.clicks
                    )
                ),
                persistentListOf(Bike.empty())
            )
        Content(state) {}
    }

@Preview
@Composable
fun SetupWizardRecMultiPreview(modifier: Modifier = Modifier) =
    AppTheme {
        val state =
            SetupWizardContract.State.Recommendation(
                buildList {
                    repeat(10) {
                        add(
                            SetupWizardContract.SetupRecommendationView(
                                0.3,
                                Res.string.front,
                                Res.string.hsc,
                                Res.string.setup_wizard_label_recommendation_increase,
                                Res.string.clicks
                            )
                        )
                    }
                }.toImmutableList(),
                persistentListOf(Bike.empty())
            )
        Content(state) {}
    }

@Preview(showBackground = true)
@Composable
fun SetupWizardSetupIssuePreview() =
    AppTheme {
        val state =
                persistentListOf(
                    SetupSymptomView(
                        Res.string.label_tire_pressure,
                        Res.string.copy_new_bike_internal_width_inf,
                        SetupSymptom.MUSH
                    ),SetupSymptomView(
                        Res.string.label_tire_pressure,
                        Res.string.copy_new_bike_internal_width_inf,
                        SetupSymptom.MUSH
                    ))
        Column {
            SetupSymptomList(state) { }
        }
    }