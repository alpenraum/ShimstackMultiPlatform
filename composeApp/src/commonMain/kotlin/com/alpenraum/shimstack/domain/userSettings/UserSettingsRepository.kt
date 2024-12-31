package com.alpenraum.shimstack.domain.userSettings

import com.alpenraum.shimstack.domain.model.PreferredTheme
import com.alpenraum.shimstack.domain.model.measurementunit.MeasurementUnitType
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getUserSettings(): Flow<UserSettings>

    suspend fun updatePreferredThemeEnabled(type: PreferredTheme)

    suspend fun updateMeasurementUnitType(type: MeasurementUnitType)

    suspend fun updateIsAnalyticsEnabled(enabled: Boolean)

    suspend fun updateIsOnboardingCompleted(enabled: Boolean)
}