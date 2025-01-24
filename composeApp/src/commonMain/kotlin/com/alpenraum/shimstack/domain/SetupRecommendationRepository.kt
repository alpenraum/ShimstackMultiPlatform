package com.alpenraum.shimstack.domain

import com.alpenraum.shimstack.domain.troubleshooting.SetupRecommendation
import kotlinx.coroutines.flow.Flow

interface SetupRecommendationRepository {
    suspend fun saveSetupRecommendation(recommendation: SetupRecommendation)

    suspend fun getSetupRecommendations(bikeId: Int): Flow<List<SetupRecommendation>>

    suspend fun updateAcceptanceState(id: Int, state: Boolean)
}