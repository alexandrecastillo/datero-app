package labs.alexandre.datero.presentation.features.dashboard.components.busline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.ui.component.BusLineFlag
import labs.alexandre.datero.presentation.ui.component.BusLineFlagScale
import labs.alexandre.datero.presentation.features.dashboard.components.busmark.EmptyBusMarks
import labs.alexandre.datero.presentation.features.dashboard.components.busmark.ListBusMarks
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.theme.Border
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.ui.preview.Data

@Composable
fun ItemBusLine(
    busLine: BusLineUiModel,
    modifier: Modifier = Modifier,
    onIntent: (DashboardIntent) -> Unit = {}
) {
    Card(
        modifier = Modifier.then(modifier),
        border = BorderStroke(1.5.dp, Border),
    ) {
        HeaderBusLine(
            busLine = busLine,
            onClickMark = { onIntent(DashboardIntent.OnClickMarkTimestampBusLine(busLine)) }
        )
        ContentBusLine(
            busLine = busLine,
            onIntent = onIntent
        )
    }
}

@Composable
fun HeaderBusLine(
    busLine: BusLineUiModel,
    onClickMark: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 18.dp, top = 8.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BusLineFlag(
                colors = busLine.colors,
                scale = BusLineFlagScale.Small
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = busLine.name, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.width(8.dp))
        FilledTonalButton(onClick = onClickMark) {
            Icon(
                painter = painterResource(R.drawable.ic_stopwatch),
                contentDescription = stringResource(R.string.dashboard_content_description_button_icon_mark)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = stringResource(R.string.dashboard_button_text_mark), style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun ContentBusLine(
    busLine: BusLineUiModel,
    onIntent: (DashboardIntent) -> Unit
) {
    when (busLine.marks.isEmpty()) {
        true -> {
            EmptyBusMarks(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
            )
        }

        false -> {
            ListBusMarks(
                busLine = busLine,
                onIntent = onIntent
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBusLineCard() {
    val item = remember { Data.busLines.first() }

    DateroTheme {
        ItemBusLine(
            busLine = item,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CardBusLinePlaceholder() {
    Card(
        border = BorderStroke(1.5.dp, Border),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(24.dp)
                    .width(186.dp)
                    .background(Color.LightGray),
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(40.dp)
                    .width(114.dp)
                    .background(Color.LightGray),
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(8) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .height(76.dp)
                        .width(62.dp)
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardBusLinePlaceholder() {
    CardBusLinePlaceholder()
}