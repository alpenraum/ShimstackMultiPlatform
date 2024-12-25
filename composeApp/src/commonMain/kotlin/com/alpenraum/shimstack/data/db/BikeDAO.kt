package com.alpenraum.shimstack.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alpenraum.shimstack.data.model.bike.BikeDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeDAO {
    @Query("SELECT * FROM ${AppDatabase.TABLE_BIKE}")
    fun getAllBikes(): Flow<List<BikeDTO>>

    @Insert
    suspend fun insertBike(bikeDTO: BikeDTO)

    @Query("SELECT * FROM ${AppDatabase.TABLE_BIKE} WHERE id = :id LIMIT 1")
    suspend fun getBike(id: Int): BikeDTO?

    @Update
    suspend fun updateBike(bikeDTO: BikeDTO)
}