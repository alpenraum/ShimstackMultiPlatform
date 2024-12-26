package com.alpenraum.shimstack.data.bike

import com.alpenraum.shimstack.data.db.BikeDAO
import com.alpenraum.shimstack.data.toDTO
import com.alpenraum.shimstack.data.toDomain
import com.alpenraum.shimstack.domain.bikeservice.BikeRepository
import com.alpenraum.shimstack.domain.model.bike.Bike
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [BikeRepository::class])
class LocalBikeRepository(
    private val bikeDAO: BikeDAO
) : BikeRepository {
    override suspend fun createBike(bike: Bike) {
        bikeDAO.insertBike(bike.toDTO())
    }

    override fun getAllBikes(): Flow<List<Bike>> = bikeDAO.getAllBikes().map { list -> list.map { it.toDomain() } }

    override suspend fun getBike(id: Int): Bike? = bikeDAO.getBike(id)?.toDomain()

    override suspend fun updateBike(bike: Bike) = bikeDAO.updateBike(bike.toDTO())
}