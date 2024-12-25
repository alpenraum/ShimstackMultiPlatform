package com.alpenraum.shimstack.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alpenraum.shimstack.data.model.bike.BikeTemplateDTO

@Dao
interface BikeTemplateDAO {
    @Insert
    suspend fun insertBike(bikes: List<BikeTemplateDTO>)

    @Query(
        "SELECT * FROM ${AppDatabase.TABLE_BIKE_TEMPLATE} WHERE name LIKE '%' || :searchTerm || '%'"
    )
    suspend fun getBikeTemplatesFilteredByName(searchTerm: String?): List<BikeTemplateDTO>
}