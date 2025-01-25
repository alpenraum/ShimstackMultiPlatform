package com.alpenraum.shimstack.ui.setupTroubleshooting

import com.alpenraum.shimstack.domain.troubleshooting.SetupSymptom
import org.jetbrains.compose.resources.StringResource
import org.koin.core.annotation.Single
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.hsc
import shimstackmultiplatform.composeapp.generated.resources.label_name

@Single
class SetupSymptomViewMapper {
    // TODO
    fun map(setupSymptom: SetupSymptom): SetupSymptomView {
        val (name, description) =
            when (setupSymptom) {
                SetupSymptom.UNDERSTEER -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.OVERSTEER -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.MUSH -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.HARSH_OVER_SMALL_BUMPS -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.BRAKE_DIVE -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.STEEP_DIVE -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.FREQUENT_BOTTOM_OUT -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.RARE_FULL_TRAVEL -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.FRONT_FLIP_ON_TAKE_OFF -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.BIKE_PACKING_DOWN -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.BIKE_FALLS_DEEP_INTO_TRAVEL -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.SLUGGISH_HANDLING -> Pair(Res.string.label_name, Res.string.hsc)
                SetupSymptom.WALLLOWY -> Pair(Res.string.label_name, Res.string.hsc)
            }

        return SetupSymptomView(name, description, setupSymptom)
    }
}

data class SetupSymptomView(
    val name: StringResource,
    val description: StringResource,
    val setupSymptom: SetupSymptom,
    val selected: Boolean = false
)