package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import labs.alexandre.datero.ui.theme.DateroTheme
import labs.alexandre.datero.ui.util.TimeFormatter
import labs.alexandre.datero.ui.util.TimeFormatter.TimeFormat

@Composable
fun TimestampItem(
    modifier: Modifier = Modifier,
    busTimestamp: BusTimestampUiModel,
    onBusTimestampClick: (busTimestamp: BusTimestampUiModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(busTimestamp.state.color)
            .clickable {
                onBusTimestampClick.invoke(busTimestamp)
            }
            .padding(8.dp, 4.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElapsedTime(
            timeFormat = TimeFormatter.transform(busTimestamp.elapsedTime)
        )
        Icon(
            painter = painterResource(busTimestamp.state.iconRes),
            contentDescription = "Icon state bus",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ElapsedTime(
    timeFormat: TimeFormat
) {
    Text(
        text = buildAnnotatedString {
            when (timeFormat) {
                is TimeFormat.Seconds -> {
                    AppendSeconds(
                        seconds = timeFormat.seconds
                    )
                }

                is TimeFormat.MinutesAndSeconds -> {
                    AppendMinutesAndSeconds(
                        minutes = timeFormat.minutes,
                        seconds = timeFormat.seconds
                    )
                }

                is TimeFormat.Minutes -> {
                    AppendMinutes(
                        minutes = timeFormat.minutes
                    )
                }

                is TimeFormat.HoursAndMinutes -> {
                    AppendHoursAndMinutes(
                        hours = timeFormat.hours,
                        minutes = timeFormat.minutes
                    )
                }
            }
        },
        color = Color.White,
        style = TextStyle(
            lineHeight = 12.sp,
            letterSpacing = (-0.5).sp,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        )
    )
}

@Composable
private fun AnnotatedString.Builder.AppendSeconds(seconds: Int) {
    withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
        append(seconds.toString())
    }
    appendLine()
    withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
        append("seg")
    }
}

@Composable
private fun AnnotatedString.Builder.AppendMinutes(minutes: Int) {
    withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
        append(minutes.toString())
    }
    appendLine()
    withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
        append("min")
    }
}


@Composable
private fun AnnotatedString.Builder.AppendMinutesAndSeconds(minutes: Int, seconds: Int) {
    withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
        append(minutes.toString())
    }
    withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
        append("m")
    }
    appendLine()
    withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
        append(seconds.toString())
    }
    withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
        append("s")
    }
}

@Composable
private fun AnnotatedString.Builder.AppendHoursAndMinutes(hours: Int, minutes: Int) {
    withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
        append(hours.toString())
    }
    withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
        append("h")
    }
    appendLine()
    withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
        append(minutes.toString())
    }
    withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
        append("m")
    }
}

@Preview
@Composable
fun PreviewTimestampItem() {
    DateroTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TimestampItem(
                busTimestamp = BusTimestampUiModel("1", 59_000, 59_000, BusUiState.OVER_FULL),
                onBusTimestampClick = {}
            )
            TimestampItem(
                busTimestamp = BusTimestampUiModel("2", 4_000, 4_000, BusUiState.FULL),
                onBusTimestampClick = {}
            )
            TimestampItem(
                busTimestamp = BusTimestampUiModel("3", 888000, 888_000, BusUiState.NORMAL),
                onBusTimestampClick = {}
            )
            TimestampItem(
                busTimestamp = BusTimestampUiModel("4", 14587777, 14_587_777, BusUiState.LOW),
                onBusTimestampClick = {}
            )
            TimestampItem(
                busTimestamp = BusTimestampUiModel("5", 566000, 566_000, BusUiState.VERY_LOW),
                onBusTimestampClick = {}
            )
        }
    }
}