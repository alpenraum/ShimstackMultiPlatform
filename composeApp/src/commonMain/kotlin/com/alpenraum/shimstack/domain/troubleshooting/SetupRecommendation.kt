package com.alpenraum.shimstack.domain.troubleshooting

import com.alpenraum.shimstack.data.model.setupRecommendation.SetupRecommendationDTO

class SetupRecommendation(
    val id: Int? = null,
    val bikeId: Int,
    val wizardSession: String,
    val frontTirePressureDelta: Double? = null,
    val rearTirePressureDelta: Double? = null,
    val frontSagDelta: Double? = null,
    val rearSagDelta: Double? = null,
    val frontHSCDelta: Double? = null,
    val rearHSCDelta: Double? = null,
    val frontLSCDelta: Double? = null,
    val rearLSCDelta: Double? = null,
    val frontHSRDelta: Double? = null,
    val rearHSRDelta: Double? = null,
    val frontLSRDelta: Double? = null,
    val rearLSRDelta: Double? = null,
    val frontTokenDelta: Double? = null,
    val rearTokenDelta: Double? = null,
    val isAccepted: Boolean? = null
) {
    fun toDto() =
        SetupRecommendationDTO(
            id = id,
            bikeId = bikeId,
            wizardSession = wizardSession,
            frontTirePressureDelta = frontTirePressureDelta,
            rearTirePressureDelta = rearTirePressureDelta,
            frontSagDelta = frontSagDelta,
            rearSagDelta = rearSagDelta,
            frontHSCDelta = frontHSCDelta,
            rearHSCDelta = rearHSCDelta,
            frontLSCDelta = frontLSCDelta,
            rearLSCDelta = rearLSCDelta,
            frontHSRDelta = frontHSRDelta,
            rearHSRDelta = rearHSRDelta,
            frontLSRDelta = frontLSRDelta,
            rearLSRDelta = rearLSRDelta,
            frontTokenDelta = frontTokenDelta,
            rearTokenDelta = rearTokenDelta,
            isAccepted = isAccepted
        )
}