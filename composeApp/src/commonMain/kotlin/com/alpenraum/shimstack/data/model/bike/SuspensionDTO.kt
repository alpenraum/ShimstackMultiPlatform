package com.alpenraum.shimstack.data.model.bike

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class SuspensionDTO(
    val pressure: Double,
    val sag: Double,
    @Embedded(prefix = "compression_") val compression: DampingDTO,
    @Embedded(prefix = "rebound_") val rebound: DampingDTO,
    val tokens: Int,
    val travel: Int
)