package com.alpenraum.shimstack.domain.bikeservice

import com.alpenraum.shimstack.domain.model.bikesetup.SetupInputData
import org.koin.core.annotation.Single

@Single
class ValidateSetupUseCase {
    operator fun invoke(setupInputData: SetupInputData): Result<SubResult> {
        val results =
            buildList {
                add(
                    setupInputData.frontTirePressure
                        ?.toDoubleOrNull()
                        ?.let { validateTirePressure(it) }
                        ?: SubResult.FAILURE
                )
                add(
                    setupInputData.rearTirePressure
                        ?.toDoubleOrNull()
                        ?.let { validateTirePressure(it) }
                        ?: SubResult.FAILURE
                )
                add(
                    setupInputData.frontSuspensionPressure
                        ?.toDoubleOrNull()
                        ?.let { validateSuspensionPressure(it, true) } ?: SubResult.FAILURE
                )
                add(
                    setupInputData.rearSuspensionPressure
                        ?.toDoubleOrNull()
                        ?.let { validateSuspensionPressure(it, false) } ?: SubResult.FAILURE
                )
                add(
                    validateSuspensionSetup(
                        setupInputData.frontSuspensionLSC?.toIntOrNull(),
                        setupInputData.frontSuspensionHSC?.toIntOrNull(),
                        setupInputData.frontSuspensionLSR?.toIntOrNull(),
                        setupInputData.frontSuspensionHSR?.toIntOrNull()
                    )
                )
                add(
                    validateSuspensionSetup(
                        setupInputData.rearSuspensionLSC?.toIntOrNull(),
                        setupInputData.rearSuspensionHSC?.toIntOrNull(),
                        setupInputData.rearSuspensionLSR?.toIntOrNull(),
                        setupInputData.rearSuspensionHSR?.toIntOrNull()
                    )
                )
            }

        return if (results.any { it == SubResult.FAILURE }) {
            Result.success(SubResult.FAILURE)
        } else {
            Result.success(if (results.any { it == SubResult.OUTLIER }) SubResult.OUTLIER else SubResult.SUCCESS)
        }
    }

    private fun validateTirePressure(pressure: Double): SubResult {
        if (pressure <= 0.0) return SubResult.FAILURE
        return if (pressure >= 3.0) SubResult.OUTLIER else SubResult.SUCCESS
    }

    private fun validateSuspensionPressure(
        pressure: Double,
        isFrontSuspension: Boolean
    ): SubResult {
        if (pressure <= 0.0) return SubResult.FAILURE
        return if (pressure < if (isFrontSuspension) 18.0 else 25.0) SubResult.SUCCESS else SubResult.OUTLIER
    }

    private fun validateSuspensionSetup(
        lsc: Int?,
        hsc: Int?,
        lsr: Int?,
        hsr: Int?
    ): SubResult {
        return if (lsc == null || lsr == null) {
            SubResult.FAILURE
        } else {
            return if (validateClicks(lsc) &&
                validateClicks(lsr) &&
                hsc?.let { validateClicks(it) } != false &&
                hsr?.let { validateClicks(it) } != false
            ) {
                SubResult.SUCCESS
            } else {
                SubResult.OUTLIER
            }
        }
    }

    private fun validateClicks(clicks: Int): Boolean = clicks in 1..29

    enum class SubResult {
        SUCCESS,
        OUTLIER,
        FAILURE
    }
}