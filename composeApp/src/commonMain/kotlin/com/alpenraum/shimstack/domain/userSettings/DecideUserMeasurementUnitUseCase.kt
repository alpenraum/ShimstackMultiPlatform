package com.alpenraum.shimstack.domain.userSettings

import androidx.compose.ui.text.intl.Locale
import com.alpenraum.shimstack.model.measurementunit.MeasurementUnitType
import org.koin.core.annotation.Single

@Single
class DecideUserMeasurementUnitUseCase(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend operator fun invoke(): MeasurementUnitType {
        val locale = Locale.current.language

        val unit =
            if (imperialRegions.any {
                    it.equals(
                        locale,
                        ignoreCase = true
                    )
                }
            ) {
                MeasurementUnitType.IMPERIAL
            } else {
                MeasurementUnitType.METRIC
            }

        userSettingsRepository.updateMeasurementUnitType(unit)
        return unit
    }

    companion object {
        private val imperialRegions = listOf("us", "uk", "mm", "lr")
    }
}