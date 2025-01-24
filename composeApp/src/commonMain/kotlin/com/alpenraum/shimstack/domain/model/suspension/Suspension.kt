package com.alpenraum.shimstack.domain.model.suspension

import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure

data class Suspension(
    val pressure: Pressure,
    val sag: Double,
    val compression: Damping,
    val rebound: Damping,
    val tokens: Int,
    val travel: Distance
) {
    constructor(travel: Int) : this(Pressure(0.0), 0.3, Damping(0), Damping(0), 0, Distance(travel.toDouble()))
}

data class Damping(
    val lowSpeedFromClosed: Int,
    val highSpeedFromClosed: Int? = null
)