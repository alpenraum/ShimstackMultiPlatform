package com.alpenraum.shimstack.domain.setupwizard.frontSuspension

import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.setupwizard.SetupAnalysisResult
import org.koin.core.annotation.Single

@Single
class IsSuspensionPressureOkUseCase {
    operator fun invoke(suspension: Suspension): SetupAnalysisResult =
        if (suspension.sag < 0.2) {
            SetupAnalysisResult.TOO_HIGH
        } else if (suspension.sag > 0.3) {
            SetupAnalysisResult.TOO_LOW
        } else {
            SetupAnalysisResult.OK
        }
}