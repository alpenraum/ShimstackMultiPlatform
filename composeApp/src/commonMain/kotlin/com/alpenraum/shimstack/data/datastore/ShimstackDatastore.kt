package com.alpenraum.shimstack.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alpenraum.shimstack.domain.model.PreferredTheme
import com.alpenraum.shimstack.domain.model.measurementunit.MeasurementUnitType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class ShimstackDatastore(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val PREF_PREFERRED_THEME = stringPreferencesKey("PREF_PREFERED_THEME")
        private val PREF_ALLOW_ANALYTICS = booleanPreferencesKey("PREF_ALLOW_ANALYTICS")
        private val PREF_IS_ONBOARDING_COMPLETED = booleanPreferencesKey("PREF_IS_ONBOARDING_COMPLETED")
        private val PREF_MEASUREMENT_UNITS_TYPE = stringPreferencesKey("PREF_MEASUREMENT_UNIT")
        private val PREF_CURRENT_WIZARD_SESSION = stringPreferencesKey("PREF_CURRENT_WIZARD_SESSION")
    }

    val preferredTheme: Flow<String> = PREF_PREFERRED_THEME.get(defaultValue = PreferredTheme.default.name)

    suspend fun setPreferredTheme(value: String) {
        dataStore.edit { it[PREF_PREFERRED_THEME] = value }
    }

    val allowAnalytics: Flow<Boolean> = PREF_ALLOW_ANALYTICS.get(defaultValue = false)

    suspend fun setAllowAnalytics(value: Boolean) {
        dataStore.edit { it[PREF_ALLOW_ANALYTICS] = value }
    }

    val isOnboardingCompleted: Flow<Boolean> =
        PREF_IS_ONBOARDING_COMPLETED.get(defaultValue = false)

    suspend fun setIsOnboardingCompleted(value: Boolean) = PREF_IS_ONBOARDING_COMPLETED.set(value)

    val measurementUnitType: Flow<String> =
        PREF_MEASUREMENT_UNITS_TYPE.get(defaultValue = MeasurementUnitType.METRIC.name)

    suspend fun setMeasurementUnit(measurementUnitType: String) = PREF_MEASUREMENT_UNITS_TYPE.set(measurementUnitType)

    val currentWizardSession: Flow<String?> =
        PREF_CURRENT_WIZARD_SESSION.getOrNull()

    suspend fun setCurrentWizardSession(wizardSession: String) = PREF_CURRENT_WIZARD_SESSION.set(wizardSession)

    private fun <T> Preferences.Key<T>.get(defaultValue: T) = dataStore.data.map { it[this] ?: defaultValue }

    private fun <T> Preferences.Key<T>.getOrNull() = dataStore.data.map { it[this] }

    private suspend fun <T> Preferences.Key<T>.set(value: T) {
        dataStore.edit { it[this] = value }
    }
}