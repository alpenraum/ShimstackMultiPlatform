package com.alpenraum.shimstack.ui.base.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastFirst
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.label_all_mtn_type
import shimstackmultiplatform.composeapp.generated.resources.label_dh_type
import shimstackmultiplatform.composeapp.generated.resources.label_enduro_type
import shimstackmultiplatform.composeapp.generated.resources.label_xc_type

@Composable
fun MultiOptionToggle(
    options: List<StringResource>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onOptionSelect: (Int) -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        shape = CircleShape
    ) {
        val animatedSelectedIndex = animateFloatAsState(selectedIndex.toFloat())
        Layout(
            modifier =
                Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHighest,
                        CircleShape
                    ).clip(CircleShape),
            content = {
                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .background(
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            ).clip(CircleShape)
                            .layoutId("SELECTOR")
                )
                options.forEachIndexed { index, option ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier =
                            Modifier
                                .clip(CircleShape)
                                .clickable {
                                    onOptionSelect(index)
                                },
                        contentAlignment = Alignment.Center
                    ) {
                        val textColor =
                            animateColorAsState(
                                targetValue =
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.onPrimary
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                            )
                        Text(
                            text = stringResource(option),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp),
                            color = textColor.value,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                measurePolicy(measurables, constraints, options.size, animatedSelectedIndex.value)
            }
        )
    }
}

private fun MeasureScope.measurePolicy(
    measurables: List<Measurable>,
    constraints: Constraints,
    optionsCount: Int,
    selectedIndex: Float
): MeasureResult {
    val elementWidth = constraints.maxWidth / optionsCount
    val elements =
        measurables.fastFilter { it.layoutId != "SELECTOR" }.fastMap {
            it.measure(constraints.copy(minWidth = elementWidth, maxWidth = elementWidth))
        }

    val layoutHeight = elements.maxOfOrNull { it.height } ?: 0
    val selector =
        measurables
            .fastFirst { it.layoutId == "SELECTOR" }
            .measure(constraints.copy(minWidth = elementWidth, maxWidth = elementWidth, minHeight = layoutHeight))

    return layout(constraints.maxWidth, layoutHeight) {
        placeContent(selector, elements, layoutHeight, constraints.maxWidth, optionsCount, selectedIndex)
    }
}

private fun Placeable.PlacementScope.placeContent(
    selector: Placeable,
    options: List<Placeable>,
    layoutHeight: Int,
    layoutWidth: Int,
    optionsCount: Int,
    selectedIndex: Float
) {
    selector.placeRelative(x = ((layoutWidth / optionsCount) * selectedIndex).toInt(), y = (layoutHeight - selector.height) / 2)
    options.fastForEachIndexed { index, option ->
        option.placeRelative(x = (layoutWidth / optionsCount) * index, y = (layoutHeight - option.height) / 2)
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewProvider::class) data: List<StringResource>
) = AppTheme {
    val selected = remember { mutableIntStateOf(0) }
    MultiOptionToggle(
        data,
        selectedIndex = selected.intValue
    ) {
        selected.intValue = it
    }
}

private class PreviewProvider : PreviewParameterProvider<List<StringResource>> {
    override val values: Sequence<List<StringResource>> =
        sequenceOf(
            listOf(Res.string.label_all_mtn_type),
            listOf(Res.string.label_all_mtn_type, Res.string.label_dh_type),
            listOf(Res.string.label_all_mtn_type, Res.string.label_dh_type, Res.string.label_xc_type),
            listOf(
                Res.string.label_all_mtn_type,
                Res.string.label_dh_type,
                Res.string.label_xc_type,
                Res.string.label_enduro_type
            )
        )
}