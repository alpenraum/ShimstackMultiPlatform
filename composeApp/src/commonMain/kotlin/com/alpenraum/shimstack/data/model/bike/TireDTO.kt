package com.alpenraum.shimstack.data.model.bike

import kotlinx.serialization.Serializable

@Serializable
data class TireDTO(
    val pressure: Double,
    val widthInMM: Double,
    val internalRimWidthInMM: Double?
)