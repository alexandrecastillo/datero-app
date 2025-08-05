package labs.alexandre.datero.presentation.features.dashboard.components.busmark

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.ui.modifier.ProgressBorderDefaults
import labs.alexandre.datero.presentation.ui.modifier.animatedProgressBorder
import labs.alexandre.datero.presentation.model.BusMarkUiModel
import labs.alexandre.datero.presentation.model.BusOccupancyUiLevel
import labs.alexandre.datero.presentation.model.ElapsedTimeUiModel
import labs.alexandre.datero.presentation.root.providers.LocalSystemClock
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.theme.Gray60
import labs.alexandre.datero.presentation.theme.SecondaryText

@Composable
@Preview(showBackground = true)
fun PreviewItemBusMark() {
    DateroTheme {
        BusMarkHistoricalItem(
            BusMarkUiModel.Historical(
                id = 1,
                busLineId = 1L,
                timestamp = 1L,
                elapsedTime = ElapsedTimeUiModel("11", "min"),
                occupancy = BusOccupancyUiLevel.LOW,
            )
        )
    }
}

@Composable
fun ColumnScope.BusMarkContent(
    busMark: BusMarkUiModel
) {
    Icon(
        painter = painterResource(R.drawable.ic_side_bus),
        contentDescription = "bus icon",
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .size(26.dp),
        tint = busMark.occupancy.color
    )
    Spacer(modifier = Modifier.height(2.dp))
    TextElapsedTime(elapsedTime = busMark.elapsedTime)
}

@Composable
fun BusMarkCurrentItem(
    busMark: BusMarkUiModel.Current,
    modifier: Modifier = Modifier,
    onProgressFinish: () -> Unit
) {
    val systemClock = LocalSystemClock.current

    val scElapsedRealtime = remember(busMark.cycle) {
        systemClock.elapsedRealtime()
    }

    val remaining = remember(scElapsedRealtime, busMark.cycle) {
        (busMark.cycle.endSystemClock - scElapsedRealtime).coerceAtLeast(0).toInt()
    }

    val progress = remember(scElapsedRealtime, busMark.cycle) {
        (((scElapsedRealtime - busMark.cycle.startSystemClock).toFloat())
            .div((busMark.cycle.endSystemClock - busMark.cycle.startSystemClock).toFloat())).coerceIn(
                0f,
                1f
            )
    }

    LaunchedEffect(Unit) {
        if (remaining == 0) {
            onProgressFinish()
        }
    }

    Box(
        modifier = Modifier
            .then(modifier)
            .animatedProgressBorder(
                progress = progress,
                duration = remaining,
                progressColor = busMark.occupancy.color,
                onFinish = { onProgressFinish() },
                key = busMark.cycle
            )
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 8.dp, bottom = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BusMarkContent(busMark = busMark)
        }
    }
}

@Composable
fun BusMarkHistoricalItem(
    busMark: BusMarkUiModel.Historical, modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .border(
                border = BorderStroke(2.dp, busMark.occupancy.color),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 8.dp, bottom = 6.dp)
            .then(modifier), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BusMarkContent(busMark = busMark)
    }
}

@Composable
fun TextElapsedTime(
    elapsedTime: ElapsedTimeUiModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ElapsedTimeDefaults.Spacing)
    ) {
        AnimatedContent(
            targetState = elapsedTime,
            transitionSpec = {
                slideInVertically { it } togetherWith slideOutVertically { -it }
            }
        ) { time ->
            Text(
                text = time.valueText, style = MaterialTheme.typography.labelLarge.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                ), textAlign = TextAlign.Center
            )
        }

        Text(
            text = elapsedTime.unitText,
            style = MaterialTheme.typography.bodySmall.copy(
                color = SecondaryText,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Modifier.animatedProgressBorder(
    progress: Float,
    duration: Int,
    progressColor: Color,
    onFinish: () -> Unit,
    key: Any
): Modifier {
    return this.animatedProgressBorder(
        style = ProgressBorderDefaults.style().copy(
            borderColor = Gray60,
            borderWidth = 2.dp,
            cornerRadius = 12.dp,
            circleRadius = 3.5.dp,
            progressColor = progressColor
        ),
        progress = progress,
        duration = duration,
        onFinish = onFinish,
        key = key
    )
}

object ElapsedTimeDefaults {
    val Spacing = (-2).dp
}