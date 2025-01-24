package com.alpenraum.shimstack.domain.troubleshooting.tire

import com.alpenraum.shimstack.domain.model.tire.Tire
import com.alpenraum.shimstack.domain.troubleshooting.SetupAnalysisResult
import org.koin.core.annotation.Single

@Single
class CalculateTirePressureOffsetForSymptomUseCase {
    operator fun invoke(tire: Tire): Double =
        when (calculateTirePressureValidity(tire)) {
            SetupAnalysisResult.OK -> -0.2
            SetupAnalysisResult.TOO_LOW -> +0.2
            SetupAnalysisResult.TOO_HIGH -> -0.4
        }

    private fun calculateTirePressureValidity(tire: Tire): SetupAnalysisResult {
        with(tire.pressure.asMetric()) {
            return if (this <
                1.0
            ) {
                SetupAnalysisResult.TOO_LOW
            } else if (this > 2.8) {
                SetupAnalysisResult.TOO_HIGH
            } else {
                SetupAnalysisResult.OK
            }
        }
    }
}