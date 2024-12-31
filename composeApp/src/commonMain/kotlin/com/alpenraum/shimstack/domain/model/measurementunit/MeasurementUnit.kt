package com.alpenraum.shimstack.domain.model.measurementunit

interface MeasurementUnit {
    fun asMetric(): Double

    fun asImperial(): Double

    val storageKey: String

    fun getAsUnit(measurementUnitType: MeasurementUnitType) =
        if (measurementUnitType.isMetric()) this.asMetric() else this.asImperial()
}