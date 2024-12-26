package com.alpenraum.shimstack.domain.model.tire

import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure

data class Tire(
    val pressure: Pressure,
    val width: Distance,
    val internalRimWidthInMM: Distance?
) {
    constructor() : this(Pressure(0.0), Distance(0.0), Distance(0.0))
}