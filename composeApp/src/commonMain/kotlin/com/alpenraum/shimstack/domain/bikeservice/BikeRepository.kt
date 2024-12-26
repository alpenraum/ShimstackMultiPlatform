package com.alpenraum.shimstack.domain.bikeservice

import com.alpenraum.shimstack.domain.model.bike.Bike
import kotlinx.coroutines.flow.Flow

interface BikeRepository {
    suspend fun createBike(bike: Bike)

    fun getAllBikes(): Flow<List<Bike>>

    suspend fun getBike(id: Int): Bike?

    suspend fun updateBike(bike: Bike)
}