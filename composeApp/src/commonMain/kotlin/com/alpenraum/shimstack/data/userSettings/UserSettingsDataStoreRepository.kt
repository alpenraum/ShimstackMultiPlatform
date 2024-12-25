package com.alpenraum.shimstack.data.userSettings

import com.alpenraum.shimstack.base.logger.ShimstackLogger
import com.alpenraum.shimstack.data.datastore.ShimstackDatastore
import com.alpenraum.shimstack.domain.userSettings.UserSettings
import com.alpenraum.shimstack.domain.userSettings.UserSettingsRepository
import com.alpenraum.shimstack.model.measurementunit.MeasurementUnitType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.core.annotation.Single

@Single
class UserSettingsDataStoreRepository(
    private val dataStore: ShimstackDatastore,
    private val logger: ShimstackLogger
) : UserSettingsRepository {
    override fun getUserSettings(): Flow<UserSettings> =
        combine(
            dataStore.allowAnalytics,
            dataStore.useDynamicTheme,
            dataStore.measurementUnitType,
            dataStore.isOnboardingCompleted
        ) { x1, x2, x3, x4 ->
            val measurementUnitType =
                try {
                    MeasurementUnitType.valueOf(x3)
                } catch (e: IllegalArgumentException) {
                    MeasurementUnitType.METRIC
                }
            UserSettings(
                isDynamicColorEnabled = x2,
                isAnalyticsEnabled = x1,
                measurementUnitType = measurementUnitType,
                isOnboardingCompleted = x4
            )
        }

    override suspend fun updateIsDynamicColorEnabled(enabled: Boolean) {
        dataStore.setUseDynamicTheme(enabled)
    }

    override suspend fun updateMeasurementUnitType(type: MeasurementUnitType) {
        logger.d("updateMeasurementUnitType: $type")
        dataStore.setMeasurementUnit(type.name)
    }

    override suspend fun updateIsAnalyticsEnabled(enabled: Boolean) {
        dataStore.setAllowAnalytics(enabled)
    }

    override suspend fun updateIsOnboardingCompleted(enabled: Boolean) {
        dataStore.setIsOnboardingCompleted(enabled)
    }
}