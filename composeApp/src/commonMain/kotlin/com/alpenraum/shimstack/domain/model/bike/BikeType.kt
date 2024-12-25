package com.alpenraum.shimstack.domain.model.bike

import org.jetbrains.compose.resources.StringResource
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.label_all_mtn_type
import shimstackmultiplatform.composeapp.generated.resources.label_dh_type
import shimstackmultiplatform.composeapp.generated.resources.label_enduro_type
import shimstackmultiplatform.composeapp.generated.resources.label_gravel_type
import shimstackmultiplatform.composeapp.generated.resources.label_road_type
import shimstackmultiplatform.composeapp.generated.resources.label_trail_type
import shimstackmultiplatform.composeapp.generated.resources.label_unknown_type
import shimstackmultiplatform.composeapp.generated.resources.label_xc_type

enum class BikeType(
    val id: Int,
    val labelRes: StringResource
) {
    GRAVEL(1, Res.string.label_gravel_type),
    XC(
        2,
        Res.string.label_xc_type
    ),
    TRAIL(
        3,
        Res.string.label_trail_type
    ),
    ALL_MTN(4, Res.string.label_all_mtn_type),
    ENDURO(5, Res.string.label_enduro_type),
    DH(
        6,
        Res.string.label_dh_type
    ),
    ROAD(7, Res.string.label_road_type),
    UNKNOWN(
        0,
        Res.string.label_unknown_type
    );

    companion object {
        fun fromId(id: Int) = entries.firstOrNull { it.id == id } ?: UNKNOWN
    }
}