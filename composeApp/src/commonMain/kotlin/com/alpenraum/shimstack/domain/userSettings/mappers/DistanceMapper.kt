package com.alpenraum.shimstack.domain.userSettings.mappers

import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.userSettings.UserSettingsRepository
import com.alpenraum.shimstack.model.measurementunit.MeasurementUnitType
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
class DistanceMapper(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend fun fromUserInput(input: Double): Distance {
        val unitType = userSettingsRepository.getUserSettings().first().measurementUnitType
        return when (unitType) {
            MeasurementUnitType.METRIC -> Distance(input)
            MeasurementUnitType.IMPERIAL -> Distance.fromImperial(input)
        }
    }

    // data layer exclusively uses metric units
    fun fromData(input: Double): Distance = Distance(input)
}