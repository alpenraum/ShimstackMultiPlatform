package com.alpenraum.shimstack.data.model.bike

import kotlinx.serialization.Serializable

@Serializable
data class DampingDTO(
    val lowSpeedFromClosed: Int,
    val highSpeedFromClosed: Int? = null
)