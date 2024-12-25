package com.alpenraum.shimstack.domain.userSettings

import com.alpenraum.shimstack.model.measurementunit.MeasurementUnitType

data class UserSettings(
    val isDynamicColorEnabled: Boolean,
    val measurementUnitType: MeasurementUnitType,
    val isAnalyticsEnabled: Boolean,
    val isOnboardingCompleted: Boolean
)