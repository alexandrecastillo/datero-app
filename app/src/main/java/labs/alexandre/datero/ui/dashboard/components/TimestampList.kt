package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BusTimestampList(
    modifier: Modifier = Modifier,
    timestamps: SnapshotStateMap<String, BusTimestampUiModel>,
    onBusTimestampClick: (BusTimestampUiModel) -> Unit
) {
    val busesList by remember { derivedStateOf { timestamps.entries.toList() } }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        busesList.forEach { (_, busTimestamp) ->
            TimestampItem(
                busTimestamp = busTimestamp,
                onBusTimestampClick = onBusTimestampClick
            )
        }
    }
}

@Preview
@Composable
fun PreviewBusTimestampList() {
    BusTimestampList(
        timestamps = SnapshotStateMap<String, BusTimestampUiModel>().apply {
            (1..8).forEach { busId ->
                this[busId.toString()] =
                    BusTimestampUiModel(
                        busId.toString(),
                        Random.nextLong(1000, 5990000),
                        BusUiState.entries[Random.nextInt(0, BusUiState.entries.size)]
                    )
            }
        },
        onBusTimestampClick = {}
    )
}
