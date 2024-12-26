package com.alpenraum.shimstack.domain.model.biketemplate

import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.model.bike.BikeType
import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.model.suspension.Damping
import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.model.tire.Tire

data class BikeTemplate(
    val id: Int?,
    val name: String,
    val type: BikeType,
    val isEBike: Boolean,
    val frontSuspensionTravelInMM: Int,
    val rearSuspensionTravelInMM: Int,
    val frontTireWidthInMM: Double,
    val frontRimWidthInMM: Double,
    val rearTireWidthInMM: Double,
    val rearRimWidthInMM: Double
) {
    companion object {
        fun testData() =
            BikeTemplate(
                id = 0,
                name = "Evil Offering V2",
                type = BikeType.ALL_MTN,
                isEBike = false,
                frontSuspensionTravelInMM = 140,
                rearSuspensionTravelInMM = 140,
                frontRimWidthInMM = 30.0,
                frontTireWidthInMM = 50.0,
                rearRimWidthInMM = 30.0,
                rearTireWidthInMM = 45.0
            )
    }

    fun toBike() =
        Bike(
            name = name,
            type = type,
            frontSuspension =
                if (frontSuspensionTravelInMM == 0) {
                    null
                } else {
                    Suspension(
                        Pressure(0.0),
                        Damping(0, 0),
                        Damping(0, 0),
                        0,
                        Distance(frontSuspensionTravelInMM.toDouble())
                    )
                },
            rearSuspension =
                if (rearSuspensionTravelInMM == 0) {
                    null
                } else {
                    Suspension(
                        Pressure(0.0),
                        Damping(0, 0),
                        Damping(0, 0),
                        0,
                        Distance(rearSuspensionTravelInMM.toDouble())
                    )
                },
            frontTire = Tire(Pressure(0.0), Distance(frontTireWidthInMM), Distance(frontRimWidthInMM)),
            rearTire =
                Tire(
                    Pressure(0.0),
                    Distance(rearTireWidthInMM),
                    Distance(rearRimWidthInMM)
                ),
            isEBike = isEBike,
            id = 0
        )
}