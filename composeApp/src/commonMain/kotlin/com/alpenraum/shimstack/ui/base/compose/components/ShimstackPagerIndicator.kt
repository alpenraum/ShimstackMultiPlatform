package com.alpenraum.shimstack.ui.base.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShimstackPagerIndicator(
    steps: Int,
    selectedStep: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    defaultColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: ((Int) -> Unit)? = null
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(steps) { step ->
            IndicatorDot(
                isSelected = step == selectedStep,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                onClick =
                    if (onClick != null) {
                        { onClick(step) }
                    } else {
                        null
                    },
                modifier =
                    Modifier
                        .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
private fun IndicatorDot(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val size = 8.dp
    val width by animateDpAsState(if (isSelected) size * 2 else size)
    val color by animateColorAsState(if (isSelected) selectedColor else defaultColor)

    Box(
        modifier =
            modifier
                .width(width)
                .height(size)
                .clip(CircleShape)
                .background(color)
                .then(
                    onClick?.let {
                        Modifier.clickable { onClick() }
                    } ?: Modifier
                )
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        var selectedStep by remember { mutableIntStateOf(3) }
        ShimstackPagerIndicator(steps = 5, selectedStep = selectedStep) {
            selectedStep = it
        }
    }
}