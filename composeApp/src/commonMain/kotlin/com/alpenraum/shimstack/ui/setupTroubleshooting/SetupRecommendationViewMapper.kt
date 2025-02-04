package com.alpenraum.shimstack.ui.setupTroubleshooting

import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.setupwizard.SetupRecommendation
import com.alpenraum.shimstack.domain.userSettings.GetUserSettingsUseCase
import com.alpenraum.shimstack.ui.bikeDetails.getPressureStringRes
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.clicks
import shimstackmultiplatform.composeapp.generated.resources.front
import shimstackmultiplatform.composeapp.generated.resources.hsc
import shimstackmultiplatform.composeapp.generated.resources.hsr
import shimstackmultiplatform.composeapp.generated.resources.label_tire_pressure
import shimstackmultiplatform.composeapp.generated.resources.lsc
import shimstackmultiplatform.composeapp.generated.resources.lsr
import shimstackmultiplatform.composeapp.generated.resources.percent
import shimstackmultiplatform.composeapp.generated.resources.rear
import shimstackmultiplatform.composeapp.generated.resources.sag
import shimstackmultiplatform.composeapp.generated.resources.setup_wizard_label_recommendation_decrease
import shimstackmultiplatform.composeapp.generated.resources.setup_wizard_label_recommendation_increase
import shimstackmultiplatform.composeapp.generated.resources.tokens
import kotlin.math.absoluteValue

@Single
class SetupRecommendationViewMapper(
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) {
    private val front = Res.string.front
    private val rear = Res.string.rear
    private val clicks = Res.string.clicks

    suspend fun map(setupRecommendation: SetupRecommendation): List<SetupWizardContract.SetupRecommendationView> =
        buildList {
            val measurementUnitType = getUserSettingsUseCase().first().measurementUnitType
            setupRecommendation.frontHSCDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        front,
                        Res.string.hsc,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.rearHSCDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        rear,
                        Res.string.hsc,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.frontLSCDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        front,
                        Res.string.lsc,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.rearLSCDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        rear,
                        Res.string.lsc,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.frontHSRDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        front,
                        Res.string.hsr,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.rearHSRDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        rear,
                        Res.string.hsr,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.frontLSRDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        front,
                        Res.string.lsr,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.rearLSRDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        rear,
                        Res.string.lsr,
                        it.getRecommendationSentence(),
                        clicks
                    )
                )
            }
            setupRecommendation.frontTirePressureDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        Pressure(it.absoluteValue).getAsUnit(measurementUnitType),
                        front,
                        Res.string.label_tire_pressure,
                        it.getRecommendationSentence(),
                        measurementUnitType.getPressureStringRes()
                    )
                )
            }
            setupRecommendation.rearTirePressureDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        Pressure(it.absoluteValue).getAsUnit(measurementUnitType),
                        rear,
                        Res.string.label_tire_pressure,
                        it.getRecommendationSentence(),
                        measurementUnitType.getPressureStringRes()
                    )
                )
            }

            setupRecommendation.frontTokenDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        front,
                        Res.string.tokens,
                        it.getRecommendationSentence(),
                        null
                    )
                )
            }
            setupRecommendation.rearTokenDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.toDouble(),
                        rear,
                        Res.string.tokens,
                        it.getRecommendationSentence(),
                        null
                    )
                )
            }
            setupRecommendation.frontSagDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.absoluteValue * 100,
                        front,
                        Res.string.sag,
                        it.getRecommendationSentence(),
                        measurementUnitType.getPressureStringRes()
                    )
                )
            }
            setupRecommendation.rearSagDelta?.let {
                add(
                    SetupWizardContract.SetupRecommendationView(
                        it.absoluteValue * 100,
                        rear,
                        Res.string.sag,
                        it.getRecommendationSentence(),
                        Res.string.percent
                    )
                )
            }
        }

    private fun Int.getRecommendationSentence() = getRecSentenceBase(this > 0)

    private fun Double.getRecommendationSentence() = getRecSentenceBase(this > 0.0)

    private fun getRecSentenceBase(isPositive: Boolean) =
        if (isPositive) {
            Res.string.setup_wizard_label_recommendation_increase
        } else {
            Res.string.setup_wizard_label_recommendation_decrease
        }
}