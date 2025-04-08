package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import labs.alexandre.datero.ui.theme.DateroTheme
import labs.alexandre.datero.ui.util.maxScrollFlingBehavior
import kotlin.random.Random

@Composable
fun BusLineList(
    modifier: Modifier = Modifier,
    busLines: SnapshotStateMap<String, BusLineUiModel>,
    onBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onMarkBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onBusTimestampClick: (busTimestampUiModel: BusTimestampUiModel) -> Unit,
    onAddBusLineClick: () -> Unit
) {
    val busLinesList by remember { derivedStateOf { busLines.entries.toList() } }

    when (busLinesList.isEmpty()) {
        true -> {
            EmptyBusLines(
                onAddBusLineClick = onAddBusLineClick
            )
        }

        false -> {
            LazyColumn(
                modifier = Modifier.then(modifier),
                flingBehavior = maxScrollFlingBehavior()
            ) {
                items(busLinesList, key = { it.key }) { (_, busLine) ->
                    BusLineItem(
                        busLine = busLine,
                        onBusLineClick = { onBusLineClick.invoke(busLine) },
                        onMarkBusLineClick = onMarkBusLineClick,
                        onBusTimestampClick = onBusTimestampClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBusLineList() {
    val busLinesList = SnapshotStateMap<String, BusLineUiModel>().apply {
        (1..8).forEach { lineId ->
            this[lineId.toString()] = BusLineUiModel(
                id = lineId.toString(),
                name = "Línea $lineId",
                colors = listOf(),
                timestamps = SnapshotStateMap<String, BusTimestampUiModel>().apply {
                    if (Random.nextInt(0, 4) != 0) {
                        (1..8).forEach { busId ->
                            this[busId.toString()] = BusTimestampUiModel(
                                id = busId.toString(),
                                elapsedTime = Random.nextLong(1000, 18_000_000),
                                state = BusUiState.entries[
                                    Random.nextInt(0, BusUiState.entries.size)
                                ]
                            )
                        }
                    }
                }
            )
        }
    }

    DateroTheme {
        BusLineList(
            busLines = busLinesList,
            onMarkBusLineClick = {},
            onBusLineClick = {},
            onBusTimestampClick = {},
            onAddBusLineClick = {}
        )
    }
}