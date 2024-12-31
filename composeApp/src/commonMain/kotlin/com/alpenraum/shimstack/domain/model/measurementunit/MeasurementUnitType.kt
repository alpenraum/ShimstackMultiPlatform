package com.alpenraum.shimstack.domain.model.measurementunit

enum class MeasurementUnitType {
    METRIC,
    IMPERIAL;

    fun isMetric() = this == METRIC
}