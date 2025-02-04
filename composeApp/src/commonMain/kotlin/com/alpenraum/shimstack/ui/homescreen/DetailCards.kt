package com.alpenraum.shimstack.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alpenraum.shimstack.domain.model.bike.Bike
import com.alpenraum.shimstack.domain.model.bike.BikeType
import com.alpenraum.shimstack.domain.model.measurementunit.Distance
import com.alpenraum.shimstack.domain.model.measurementunit.Pressure
import com.alpenraum.shimstack.domain.model.suspension.Damping
import com.alpenraum.shimstack.domain.model.suspension.Suspension
import com.alpenraum.shimstack.domain.model.tire.Tire
import com.alpenraum.shimstack.ui.base.compose.AdaptiveSizeText
import com.alpenraum.shimstack.ui.base.compose.components.CARD_DIMENSION
import com.alpenraum.shimstack.ui.base.compose.components.CARD_MARGIN
import com.alpenraum.shimstack.ui.base.compose.components.ShimstackCard
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.bikeDetails.getFrontSuspensionUIData
import com.alpenraum.shimstack.ui.bikeDetails.getRearSuspensionUIData
import com.alpenraum.shimstack.ui.bikeDetails.getTireUIData
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.copy_no_fork
import shimstackmultiplatform.composeapp.generated.resources.copy_no_shock
import shimstackmultiplatform.composeapp.generated.resources.fork
import shimstackmultiplatform.composeapp.generated.resources.front
import shimstackmultiplatform.composeapp.generated.resources.rear
import shimstackmultiplatform.composeapp.generated.resources.shock
import shimstackmultiplatform.composeapp.generated.resources.tire

@Composable
fun TireDetails(
    bigCard: Boolean,
    bike: Bike,
    isMetric: Boolean,
    modifier: Modifier = Modifier
) {
    DetailsCard(title = Res.string.tire, bigCard, modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 16.dp)
                    .weight(1.0f),
            horizontalArrangement = Arrangement.Center
        ) {
            val data = bike.getTireUIData(isMetric)
            SimpleTextPair(
                heading = stringResource(Res.string.front),
                content = data.first.content,
                bigCard = bigCard,
                modifier = Modifier.weight(1.0f)
            )
            VerticalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SimpleTextPair(
                heading = stringResource(Res.string.rear),
                content = data.second.content,
                bigCard = bigCard,
                modifier = Modifier.weight(1.0f)
            )
        }
    }
}

@Composable
private fun SimpleTextPair(
    heading: String,
    content: String,
    bigCard: Boolean,
    modifier: Modifier = Modifier
) = Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
    AdaptiveSizeText(
        text = heading,
        style =
            MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
        modifier = Modifier.padding(bottom = 4.dp),
        textAlign = TextAlign.Center
    )
    if (bigCard) {
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ForkDetails(
    bigCard: Boolean,
    bike: Bike,
    isMetric: Boolean,
    modifier: Modifier = Modifier
) {
    SuspensionDetails(
        bigCard = bigCard,
        suspensionData = bike.getFrontSuspensionUIData(isMetric),
        titleRes = Res.string.fork,
        errorTextRes = Res.string.copy_no_fork,
        modifier = modifier
    )
}

@Composable
fun ShockDetails(
    bigCard: Boolean,
    bike: Bike,
    isMetric: Boolean,
    modifier: Modifier = Modifier
) {
    SuspensionDetails(
        bigCard = bigCard,
        suspensionData = bike.getRearSuspensionUIData(isMetric),
        titleRes = Res.string.shock,
        errorTextRes = Res.string.copy_no_shock,
        modifier = modifier
    )
}

@Composable
private fun SuspensionDetails(
    bigCard: Boolean,
    titleRes: StringResource,
    errorTextRes: StringResource,
    suspensionData: ImmutableList<UIDataLabel>?,
    modifier: Modifier = Modifier
) {
    DetailsCard(title = titleRes, bigCard = bigCard, modifier = modifier) {
        suspensionData?.let {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(top = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .weight(1.0f)
                ) {
                    SuspensionQuarter(data = it[0], modifier = Modifier.weight(1.0f))
                    SuspensionQuarter(data = it[1], modifier = Modifier.weight(1.0f))
                }
                HorizontalDivider()
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .weight(1.0f)
                ) {
                    SuspensionQuarter(data = it[2], modifier = Modifier.weight(1.0f))
                    SuspensionQuarter(data = it[3], modifier = Modifier.weight(1.0f))
                }
            }
        } ?: run {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(errorTextRes),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SuspensionQuarter(
    data: UIDataLabel,
    modifier: Modifier = Modifier
) {
    when (data) {
        is UIDataLabel.Simple -> {
            SimpleTextPair(
                heading = data.heading,
                content = data.content,
                bigCard = false,
                modifier = modifier
            )
        }

        is UIDataLabel.Complex -> {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                data.data.forEach {
                    Row(modifier = Modifier.padding(bottom = 4.dp)) {
                        Text(
                            text = it.key,
                            style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.outline
                                ),
                            modifier = Modifier.padding(end = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = it.value,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailsCard(
    title: StringResource,
    bigCard: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    ShimstackCard(
        modifier =
            modifier
                .height(IntrinsicSize.Min)
                .width(if (bigCard) CARD_DIMENSION * 2.0f + CARD_MARGIN else CARD_DIMENSION * 1.0f)
                .padding(vertical = CARD_MARGIN / 2.0f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp, top = 4.dp)
            )
        }
    }
}

@Preview()
@Composable
private fun PreviewTireData() {
    AppTheme {
        TireDetails(bigCard = false, Bike.empty(), false)
    }
}

@Preview()
@Composable
private fun PreviewTireDataBig() {
    AppTheme {
        TireDetails(bigCard = true, Bike.empty(), true)
    }
}

private val testBike =
    Bike(
        name = "1",
        type = BikeType.UNKNOWN,
        frontSuspension =
            Suspension(
                Pressure(60.0),
                0.3,
                Damping(1),
                Damping(1),
                3,
                Distance(140.0)
            ),
        frontTire =
            Tire(
                Pressure(20.0),
                Distance(0.0),
                Distance(0.0)
            ),
        rearTire = Tire(Pressure(20.0), Distance(0.0), Distance(0.0)),
        isEBike = false,
        id = 0
    )
private val testBikeMax =
    Bike(
        name = "1",
        type = BikeType.UNKNOWN,
        frontSuspension =
            Suspension(
                Pressure(60.0),
                0.3,
                Damping(1, 2),
                Damping(3, 4),
                5,
                Distance(140.0)
            ),
        frontTire =
            Tire(
                Pressure(20.0),
                Distance(0.0),
                Distance(0.0)
            ),
        rearTire = Tire(Pressure(20.0), Distance(0.0), Distance(0.0)),
        isEBike = false,
        id = 0
    )

@Preview
@Composable
private fun PreviewForkData() = ForkDetails(bigCard = false, bike = testBike, true)

@Preview
@Composable
private fun PreviewForkDataBig() = ForkDetails(bigCard = true, bike = testBike, false)

@Preview
@Composable
private fun PreviewForkDataMax() = ForkDetails(bigCard = false, bike = testBikeMax, true)

@Preview
@Composable
private fun PreviewForkDataMaxBig() = ForkDetails(bigCard = true, bike = testBikeMax, false)