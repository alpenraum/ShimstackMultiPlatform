package com.alpenraum.shimstack.domain.userSettings

import com.alpenraum.shimstack.domain.model.PreferredTheme
import com.alpenraum.shimstack.domain.model.measurementunit.MeasurementUnitType

data class UserSettings(
    val preferredTheme: PreferredTheme,
    val measurementUnitType: MeasurementUnitType,
    val isAnalyticsEnabled: Boolean,
    val isOnboardingCompleted: Boolean
)