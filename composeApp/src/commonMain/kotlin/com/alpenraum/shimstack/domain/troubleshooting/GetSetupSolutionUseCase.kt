package com.alpenraum.shimstack.domain.troubleshooting

import com.alpenraum.shimstack.domain.SetupRecommendationRepository
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.troubleshooting.tire.CalculateTirePressureOffsetForSymptomUseCase
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * V1 - hard coded problem solving
 * V2 - when collected enough data, make it based on machine learning categorisation
 */
@Single
class GetSetupSolutionUseCase(
    private val setupRecommendationRepository: SetupRecommendationRepository,
    private val calculateTirePressureOffsetForSymptomUseCase: CalculateTirePressureOffsetForSymptomUseCase
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(
        issue: SetupSymptom,
        bike: Bike,
        isFront: Boolean?,
        isOnHighSpeed: Boolean?
    ): SetupRecommendation {
        val currentWizardSession =
            setupRecommendationRepository.getOpenWizardSessionForBike(bikeId = bike.id ?: -1) ?: Uuid.random().toHexString()

        val recommendation =
            when (issue) {
                SetupSymptom.UNDERSTEER ->
                    SetupRecommendation(
                        wizardSession = currentWizardSession,
                        bikeId = bike.id ?: -1,
                        frontTirePressureDelta = calculateTirePressureOffsetForSymptomUseCase(bike.frontTire)
                    )

                SetupSymptom.OVERSTEER ->
                    SetupRecommendation(
                        wizardSession = currentWizardSession,
                        bikeId = bike.id ?: -1,
                        rearTirePressureDelta = calculateTirePressureOffsetForSymptomUseCase(bike.rearTire)
                    )

                SetupSymptom.MUSH -> TODO()
                SetupSymptom.HARSH_OVER_SMALL_BUMPS -> TODO()
                SetupSymptom.BRAKE_DIVE -> TODO()
                SetupSymptom.STEEP_DIVE -> TODO()
                SetupSymptom.FREQUENT_BOTTOM_OUT -> TODO()
                SetupSymptom.RARE_FULL_TRAVEL -> TODO()
                SetupSymptom.FRONT_FLIP_ON_TAKE_OFF -> TODO()
                SetupSymptom.BIKE_PACKING_DOWN -> TODO()
                SetupSymptom.BIKE_FALLS_DEEP_INTO_TRAVEL -> TODO()
            }

        setupRecommendationRepository.saveSetupRecommendation(recommendation)

        return recommendation
    }
}