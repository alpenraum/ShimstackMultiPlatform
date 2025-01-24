package com.alpenraum.shimstack.ui.base.compose.components.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices.FOLDABLE
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Preview
import com.alpenraum.shimstack.ui.homescreen.Preview

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Preview(name = "Phone", device = PHONE, showSystemUi = true)
@Preview(name = "Unfolded Foldable", device = FOLDABLE, showSystemUi = true)
// @Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Preview(name = "100%", fontScale = 1.0f, showBackground = true)
@Preview(name = "180%", fontScale = 1.8f, showBackground = true)
annotation class ShimstackPreviews

@ShimstackPreviews
@Composable
fun HomescreenFeaturePreview() {
    Preview()
}