package com.alpenraum.shimstack.ui.base.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.alpenraum.shimstack.base.logger.ShimstackLogger
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.fade
import com.eygraber.compose.placeholder.placeholder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.fab_add_bike
import kotlin.math.absoluteValue
import kotlin.math.max

@Composable
fun BikePager(
    modifier: Modifier = Modifier,
    bikes: ImmutableList<Bike?>,
    showPlaceholder: Boolean,
    pagerState: PagerState,
    onAddNewBikeClick: (() -> Unit)?,
    onDetailsClick: ((Bike) -> Unit)?,
    onSelectedBikeChanged: (Int) -> Unit
) {
    val itemSize = 200.dp

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect {
            onSelectedBikeChanged(it)
        }
    }
    Column(modifier = modifier) {
        HorizontalPager(
            pagerState,
            modifier = Modifier,
            contentPadding =
                PaddingValues(
                    horizontal = calculatePagerItemPadding(itemWidth = itemSize)
                ),
            verticalAlignment = Alignment.Top,
            userScrollEnabled = !showPlaceholder
        ) { page ->
            val bike = bikes.getOrNull(page)

            val content: @Composable (modifier: Modifier) -> Unit =
                if (page == bikes.size && onAddNewBikeClick != null) {
                    { AddNewBikeCardContent(onAddNewBikeClick = onAddNewBikeClick) }
                } else {
                    { BikeCardContent(bike = bike, it) }
                }

            BikeCard(
                modifier =
                    Modifier
                        .size(itemSize)
                        .graphicsLayer {
                            val pageOffset =
                                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                                    .absoluteValue
                            lerp(
                                start = 0.8f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                this.scaleX = scale
                                this.scaleY = scale
                            }
                        }.clickable {
                            bike?.let { b ->
                                onDetailsClick?.let {
                                    it(b)
                                }
                            }
                        },
                showPlaceholder = showPlaceholder,
                content = content
            )
        }
        ShimstackPagerIndicator(
            selectedStep = pagerState.currentPage,
            steps = pagerState.pageCount,
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun calculatePagerItemPadding(itemWidth: Dp) =
    max(
        ((getScreenWidth().value - itemWidth.value) / 2.0f),
        0.0f
    ).dp

@Composable
fun BikeCard(
    modifier: Modifier = Modifier,
    showPlaceholder: Boolean,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val logger = getKoin().get<ShimstackLogger>()
    LaunchedEffect(showPlaceholder) {
        logger.d("show placeholder: $showPlaceholder")
    }
    Surface(
        modifier =
            modifier
                .placeholder(
                    visible = showPlaceholder,
                    highlight = PlaceholderHighlight.fade(),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = shimstackRoundedCornerShape()
                ),
        shape = shimstackRoundedCornerShape(),
        tonalElevation = 10.dp,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        content(Modifier.padding(8.dp))
    }
}

@Composable
fun BikeCardContent(
    bike: Bike?,
    modifier: Modifier = Modifier
) {
    // TODO
    Text(
        bike?.name ?: "",
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AddNewBikeCardContent(
    modifier: Modifier = Modifier,
    onAddNewBikeClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxSize()
                .clickable {
                    onAddNewBikeClick()
                }.semantics(mergeDescendants = true) {}
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "",
                modifier =
                    Modifier
                        .size(100.dp)
                        .semantics { invisibleToUser() },
                tint = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.8f)
            )
            Text(
                text = stringResource(Res.string.fab_add_bike),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}