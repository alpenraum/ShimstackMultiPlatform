package com.alpenraum.shimstack.ui.newBike.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.alpenraum.shimstack.ui.base.compose.components.DecisionButtonConfig
import com.alpenraum.shimstack.ui.base.compose.components.DecisionScreen
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.newBike.NewBikeDestinations
import org.jetbrains.compose.ui.tooling.preview.Preview
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.copy_new_bike_existing_setup
import shimstackmultiplatform.composeapp.generated.resources.label_no
import shimstackmultiplatform.composeapp.generated.resources.label_yes

@Composable
fun SetupDecisionScreen(navController: NavController? = null) {
    DecisionScreen(
        imageRes = null,
        contentRes = Res.string.copy_new_bike_existing_setup,
        modifier = Modifier,
        buttons =
            listOf(
                DecisionButtonConfig(Res.string.label_yes, true) {
                    navController?.navigate(NewBikeDestinations.ENTER_SETUP.route)
                },
                DecisionButtonConfig(Res.string.label_no, false) {
                    // TODO: Add ThiS Toast.makeText(context, "TODO: Insert Setup Wizard here", Toast.LENGTH_LONG).show()
                }
            )
    )
}

@Preview
@Composable
private fun SetupDecisionScreenPreview() {
    AppTheme {
        SetupDecisionScreen(null)
    }
}