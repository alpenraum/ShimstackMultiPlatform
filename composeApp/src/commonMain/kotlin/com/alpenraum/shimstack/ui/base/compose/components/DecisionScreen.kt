package com.alpenraum.shimstack.ui.base.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.ic_save
import shimstackmultiplatform.composeapp.generated.resources.tokens

class DecisionButtonConfig(
    val label: StringResource,
    val isPrimaryButton: Boolean,
    val onClick: () -> Unit
)

@Composable
fun DecisionScreen(
    imageRes: DrawableResource?,
    contentRes: StringResource,
    buttons: List<DecisionButtonConfig>,
    modifier: Modifier = Modifier
) {
    DecisionScreen(imageContent = {
        imageRes?.let { Image(painter = painterResource(it), contentDescription = null) }
    }, contentRes = contentRes, modifier = modifier, buttons = buttons)
}

@Composable
fun DecisionScreen(
    imageContent: @Composable () -> Unit,
    contentRes: StringResource,
    modifier: Modifier = Modifier,
    buttons: List<DecisionButtonConfig>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxHeight()
    ) {
        imageContent()
        Text(
            text = stringResource(contentRes),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        buttons.forEach {
            val label = @Composable { ButtonText(it.label) }
            if (it.isPrimaryButton) {
                LargeButton(onClick = it.onClick, modifier = Modifier.padding(bottom = 8.dp)) {
                    label()
                }
            } else {
                LargeSecondaryButton(
                    onClick = it.onClick,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    label()
                }
            }
        }
    }
}

@Preview
@Composable
private fun DecisionScreenPreview() {
    AppTheme {
        DecisionScreen(
            imageRes = Res.drawable.ic_save,
            contentRes = Res.string.tokens,
            listOf(
                DecisionButtonConfig(Res.string.tokens, true) {},
                DecisionButtonConfig(Res.string.tokens, false) {},
                DecisionButtonConfig(Res.string.tokens, false) {},
                DecisionButtonConfig(Res.string.tokens, true) {}
            )
        )
    }
}