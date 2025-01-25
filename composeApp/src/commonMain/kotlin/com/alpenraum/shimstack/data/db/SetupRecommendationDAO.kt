package com.alpenraum.shimstack.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alpenraum.shimstack.data.model.setupRecommendation.SetupRecommendationDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface SetupRecommendationDAO {
    @Insert
    suspend fun insertRecommendation(recommendation: SetupRecommendationDTO)

    @Query(
        "SELECT * FROM ${AppDatabase.TABLE_SETUP_RECOMMENDATION} WHERE bikeId = :bikeId ORDER BY creation_epoch_seconds DESC"
    )
    fun getLastSetupRecommendations(bikeId: Int): Flow<List<SetupRecommendationDTO>>

    @Query("UPDATE ${AppDatabase.TABLE_SETUP_RECOMMENDATION} SET isAccepted = :state WHERE :id = id")
    suspend fun updateAcceptanceState(
        id: Int,
        state: Boolean
    )

    @Query(
        "SELECT wizardSession FROM ${AppDatabase.TABLE_SETUP_RECOMMENDATION} WHERE bikeId = :bikeId AND isAccepted IS NULL ORDER BY creation_epoch_seconds DESC LIMIT 1"
    )
    fun getOpenWizardSessionForBike(bikeId: Int): String?
}