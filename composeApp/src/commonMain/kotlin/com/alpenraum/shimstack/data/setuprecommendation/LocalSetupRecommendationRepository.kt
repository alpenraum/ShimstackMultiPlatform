package com.alpenraum.shimstack.data.setuprecommendation

import com.alpenraum.shimstack.data.db.SetupRecommendationDAO
import com.alpenraum.shimstack.domain.SetupRecommendationRepository
import com.alpenraum.shimstack.domain.troubleshooting.SetupRecommendation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocalSetupRecommendationRepository(
    private val setupRecommendationDAO: SetupRecommendationDAO
) : SetupRecommendationRepository {
    override suspend fun saveSetupRecommendation(recommendation: SetupRecommendation) {
        val dto = recommendation.toDto()
        setupRecommendationDAO.insertRecommendation(dto)
    }

    override suspend fun getSetupRecommendations(bikeId: Int): Flow<List<SetupRecommendation>> =
        setupRecommendationDAO.getLastSetupRecommendations(bikeId).map { list ->
            list.map {
                it.toDomain()
            }
        }

    override suspend fun updateAcceptanceState(
        id: Int,
        state: Boolean
    ) {
        setupRecommendationDAO.updateAcceptanceState(id, state)
    }

    override suspend fun getOpenWizardSessionForBike(bikeId: Int): String? =
        setupRecommendationDAO.getOpenWizardSessionForBike(bikeId)
}