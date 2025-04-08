package labs.alexandre.datero.ui.dashboard.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import labs.alexandre.datero.ui.theme.DateroTheme
import kotlin.random.Random

@Composable
fun ButtonMark(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        contentPadding = PaddingValues(12.dp, 8.dp, 18.dp, 8.dp),
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_stopwatch),
            contentDescription = "Icono cronometro",
            modifier = Modifier.size(22.5.dp, 21.25.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Marcar",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewButtonMark() {
    DateroTheme {
        ButtonMark(onClick = {})
    }
}

@Composable
fun HeaderBusLine(
    busLine: BusLineUiModel,
    modifier: Modifier = Modifier,
    onBusLineClick: () -> Unit,
    onMarkBusLineClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onBusLineClick.invoke()
                }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BusLineFlag(
                colors = busLine.colors
            )
            Text(
                text = busLine.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_see_more),
                contentDescription = "Ver todo los tiempos de ${busLine.name}"
            )
        }
        ButtonMark(
            onClick = onMarkBusLineClick
        )
    }
}

@Composable
@Preview
fun PreviewHeaderBusLine() {
    DateroTheme {
        HeaderBusLine(
            modifier = Modifier,
            busLine = BusLineUiModel(
                "1",
                "Línea E",
                colors = listOf(
                    Color(0xFFFFFFFF), Color(0xFFFFB344)
                ),
                timestamps = SnapshotStateMap<String, BusTimestampUiModel>().apply {
                    (1..8).forEach { busId ->
                        this[busId.toString()] = BusTimestampUiModel(
                            busId.toString(),
                            Random.nextLong(1000, 5990000),
                            BusUiState.entries[Random.nextInt(0, BusUiState.entries.size)]
                        )
                    }
                }),
            onBusLineClick = {},
            onMarkBusLineClick = {}
        )
    }
}

@Composable
fun BusLineItem(
    modifier: Modifier = Modifier,
    busLine: BusLineUiModel,
    onBusLineClick: () -> Unit,
    onMarkBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onBusTimestampClick: (BusTimestampUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderBusLine(
            busLine = busLine,
            onBusLineClick = onBusLineClick,
            onMarkBusLineClick = { onMarkBusLineClick.invoke(busLine) }
        )
        ContentBusLine(
            timestamps = busLine.timestamps,
            onBusTimestampClick = onBusTimestampClick
        )
    }
}

@Composable
fun ContentBusLine(
    modifier: Modifier = Modifier,
    timestamps: SnapshotStateMap<String, BusTimestampUiModel>,
    onBusTimestampClick: (busTimestampUiModel: BusTimestampUiModel) -> Unit
) {
    val timestampsList by remember { derivedStateOf { timestamps.entries } }

    when (timestampsList.isEmpty()) {
        true -> {
            EmptyTimestamps(
                modifier = modifier
            )
        }

        false -> {
            BusTimestampList(
                modifier = modifier,
                timestamps = timestamps,
                onBusTimestampClick = onBusTimestampClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBusLineItem() {
    DateroTheme {
        BusLineItem(
            busLine = BusLineUiModel(
                id = "1",
                name = "Línea Chama",
                colors = listOf(
                    Color(0xFFD73939),
                    Color(0xFF196320),
                    Color(0xFF20882B),
                    Color(0xFFF8F8F8),
                    Color(0xFFD73939)
                ),
                timestamps = SnapshotStateMap<String, BusTimestampUiModel>().apply {
                    (1..8).forEach { busId ->
                        this[busId.toString()] = BusTimestampUiModel(
                            busId.toString(),
                            Random.nextLong(1000, 5990000),
                            BusUiState.entries[Random.nextInt(0, BusUiState.entries.size)]
                        )
                    }
                }),
            onBusLineClick = {},
            onMarkBusLineClick = {},
            onBusTimestampClick = {}
        )
    }
}