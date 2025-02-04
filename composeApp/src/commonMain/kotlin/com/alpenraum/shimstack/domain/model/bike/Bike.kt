package com.alpenraum.shimstack.domain.model.bike

import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.model.tire.Tire
import com.alpenraum.shimstack.domain.setupwizard.SetupRecommendation

data class Bike(
    val id: Int? = null,
    val name: String,
    val type: BikeType,
    val frontSuspension: Suspension? = null,
    val rearSuspension: Suspension? = null,
    val frontTire: Tire,
    val rearTire: Tire,
    val isEBike: Boolean
) {
    fun copyWithSetupRecommendation(setupRecommendation: SetupRecommendation): Bike {
        val newFrontTire =
            if (setupRecommendation.frontTirePressureDelta != null) {
                frontTire.copy(
                    pressure =
                        Pressure(
                            setupRecommendation.frontTirePressureDelta + frontTire.pressure.asMetric()
                        )
                )
            } else {
                frontTire
            }
        val newRearTire =
            if (setupRecommendation.rearTirePressureDelta != null) {
                rearTire.copy(
                    pressure = Pressure(setupRecommendation.rearTirePressureDelta + rearTire.pressure.asMetric())
                )
            } else {
                rearTire
            }

        val newFrontSuspension =
            if ((
                    setupRecommendation.frontSagDelta != null ||
                        setupRecommendation.frontHSCDelta != null ||
                        setupRecommendation.frontHSRDelta != null ||
                        setupRecommendation.frontLSCDelta != null ||
                        setupRecommendation.frontLSRDelta != null ||
                        setupRecommendation.frontTokenDelta != null
                ) &&
                frontSuspension != null
            ) {
                val newCompression =
                    frontSuspension.compression.copy(
                        highSpeedFromClosed =
                            frontSuspension.compression.highSpeedFromClosed?.let {
                                it + (setupRecommendation.frontHSCDelta ?: 0)
                            },
                        lowSpeedFromClosed =
                            frontSuspension.compression.lowSpeedFromClosed + (setupRecommendation.frontLSCDelta ?: 0)
                    )
                val newRebound =
                    frontSuspension.rebound.copy(
                        highSpeedFromClosed =
                            frontSuspension.rebound.highSpeedFromClosed?.let {
                                it + (setupRecommendation.frontHSRDelta ?: 0)
                            },
                        lowSpeedFromClosed = frontSuspension.rebound.lowSpeedFromClosed + (setupRecommendation.frontLSRDelta ?: 0)
                    )
                frontSuspension.copy(
                    sag = frontSuspension.sag + (setupRecommendation.frontSagDelta ?: 0.0),
                    tokens = frontSuspension.tokens + (setupRecommendation.frontTokenDelta ?: 0),
                    compression = newCompression,
                    rebound = newRebound
                )
            } else {
                frontSuspension
            }

        val newRearSuspension =
            if ((
                    setupRecommendation.rearSagDelta != null ||
                        setupRecommendation.rearHSCDelta != null ||
                        setupRecommendation.rearHSRDelta != null ||
                        setupRecommendation.rearLSCDelta != null ||
                        setupRecommendation.rearLSRDelta != null ||
                        setupRecommendation.rearTokenDelta != null
                ) &&
                rearSuspension != null
            ) {
                val newCompression =
                    rearSuspension.compression.copy(
                        highSpeedFromClosed =
                            rearSuspension.compression.highSpeedFromClosed?.let {
                                it + (setupRecommendation.rearHSCDelta ?: 0)
                            },
                        lowSpeedFromClosed =
                            rearSuspension.compression.lowSpeedFromClosed + (setupRecommendation.rearLSCDelta ?: 0)
                    )
                val newRebound =
                    rearSuspension.rebound.copy(
                        highSpeedFromClosed =
                            rearSuspension.rebound.highSpeedFromClosed?.let {
                                it + (setupRecommendation.rearHSRDelta ?: 0)
                            },
                        lowSpeedFromClosed = rearSuspension.rebound.lowSpeedFromClosed + (setupRecommendation.rearLSRDelta ?: 0)
                    )
                rearSuspension.copy(
                    sag = rearSuspension.sag + (setupRecommendation.rearSagDelta ?: 0.0),
                    tokens = rearSuspension.tokens + (setupRecommendation.rearTokenDelta ?: 0),
                    compression = newCompression,
                    rebound = newRebound
                )
            } else {
                rearSuspension
            }

        return this.copy(
            frontTire = newFrontTire,
            rearTire = newRearTire,
            frontSuspension = newFrontSuspension,
            rearSuspension = newRearSuspension
        )
    }

    companion object {
        fun empty() =
            Bike(
                name = "",
                type = BikeType.UNKNOWN,
                isEBike = false,
                frontTire = Tire(Pressure(0.0), Distance(0.0), Distance(0.0)),
                rearTire = Tire(Pressure(0.0), Distance(0.0), Distance(0.0)),
                frontSuspension = null,
                rearSuspension = null,
                id = 0
            )
    }
}