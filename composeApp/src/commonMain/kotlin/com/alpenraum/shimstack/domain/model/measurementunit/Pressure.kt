package com.alpenraum.shimstack.domain.model.measurementunit

data class Pressure(
    private val pressureInBar: Double
) : MeasurementUnit {
    companion object {
        private const val BAR_TO_PSI_CONVERSION = 14.503773773

        fun fromImperial(pressure: Double) = Pressure(pressure / BAR_TO_PSI_CONVERSION)
    }

    fun isEmpty() = pressureInBar != 0.0

    override fun asMetric(): Double = pressureInBar

    override fun asImperial(): Double = (pressureInBar * BAR_TO_PSI_CONVERSION)

    override val storageKey = "PREF_PRESSURE_UNIT"
}