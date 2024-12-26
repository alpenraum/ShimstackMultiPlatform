package com.alpenraum.shimstack.domain.model.bikesetup

// TODO: move to UI layer
data class DetailsInputData(
    val name: String? = null,
    val frontTravel: String? = null,
    val rearTravel: String? = null,
    val frontTireWidth: String? = null,
    val rearTireWidth: String? = null,
    val frontInternalRimWidth: String? = null,
    val rearInternalRimWidth: String? = null
)