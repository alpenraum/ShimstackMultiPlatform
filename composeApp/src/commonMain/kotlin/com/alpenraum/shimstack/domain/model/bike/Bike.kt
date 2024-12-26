package com.alpenraum.shimstack.domain.model.bike

import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.model.tire.Tire

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