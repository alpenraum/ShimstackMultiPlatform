package com.alpenraum.shimstack.ui.newBike.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.navigation.NavController
import com.alpenraum.shimstack.ui.base.compose.components.DecisionButtonConfig
import com.alpenraum.shimstack.ui.base.compose.components.DecisionScreen
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.newBike.NewBikeContract
import com.alpenraum.shimstack.ui.newBike.NewBikeDestinations
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.copy_new_bike_success
import shimstackmultiplatform.composeapp.generated.resources.il_mountain_biker
import shimstackmultiplatform.composeapp.generated.resources.label_add_another_bike
import shimstackmultiplatform.composeapp.generated.resources.label_done

@Composable
fun NewBikeSuccessScreen(
    navigator: NavController? = null,
    intent: (NewBikeContract.Intent) -> Unit
) {
    // TODO: ADD BACK ONCE GOOGLE ADDS THIS FEATURE
//    BackHandler {
//        intent(NewBikeContract.Intent.OnFlowFinished)
//    }
    DecisionScreen(
        imageContent = {
            Image(
                painter = painterResource(Res.drawable.il_mountain_biker),
                contentDescription = null,
                modifier = Modifier.scale(0.7f)
            )
        },
        contentRes = Res.string.copy_new_bike_success,
        modifier = Modifier,
        buttons =
            listOf(
                DecisionButtonConfig(Res.string.label_done, true) {
                    intent(NewBikeContract.Intent.OnFlowFinished)
                },
                DecisionButtonConfig(Res.string.label_add_another_bike, false) {
                    navigator?.navigate(NewBikeDestinations.ENTRY.route) {
                        popUpTo(NewBikeDestinations.ENTRY.route)
                    }
                }
            )
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        NewBikeSuccessScreen(null) {}
    }
}