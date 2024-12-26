package com.alpenraum.shimstack.ui.base.compose.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class BottomSheetAppearance {
    FULL_SCREEN,
    COMPACT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShimstackBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    appearance: BottomSheetAppearance = BottomSheetAppearance.COMPACT,
    sheetContent: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        modifier =
            modifier
                .animateContentSize(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        content = {
            when (appearance) {
                BottomSheetAppearance.FULL_SCREEN -> {
                    val screenHeight = getScreenHeight()
                    Column(Modifier.height(screenHeight - 64.dp)) {
                        sheetContent()
                    }
                }

                BottomSheetAppearance.COMPACT -> sheetContent()
            }
        }
    )
}