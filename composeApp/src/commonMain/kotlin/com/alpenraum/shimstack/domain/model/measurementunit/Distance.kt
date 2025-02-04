package com.alpenraum.shimstack.domain.model.measurementunit

import com.alpenraum.shimstack.base.round

data class Distance(
    private val distanceInMM: Double
) : MeasurementUnit {
    override fun asMetric(): Double = distanceInMM

    override fun asImperial(): Double = (distanceInMM * MM_TO_INCH_CONVERSION).round(1)

    override val storageKey = "PREF_DISTANCE_UNIT"

    companion object {
        const val MM_TO_INCH_CONVERSION = (3.0 / 64.0)

        fun fromImperial(distance: Double) = Distance(distance / MM_TO_INCH_CONVERSION)
    }
}