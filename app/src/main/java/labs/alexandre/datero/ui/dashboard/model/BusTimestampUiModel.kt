package labs.alexandre.datero.ui.dashboard.model

import androidx.compose.runtime.snapshots.SnapshotStateMap
import labs.alexandre.datero.domain.model.Timestamp

data class BusTimestampUiModel(
    val id: String,
    val timestamp: Long,
    val elapsedTime: Long,
    val state: BusUiState
)

fun List<Timestamp>.toSnapshotStateMap(): SnapshotStateMap<String, BusTimestampUiModel> {
    return SnapshotStateMap<String, BusTimestampUiModel>().apply {
        this@toSnapshotStateMap.forEach { timestamp ->
            this[timestamp.id.toString()] =
                BusTimestampUiModel(
                    id = timestamp.id.toString(),
                    timestamp = timestamp.timestamp,
                    elapsedTime = System.currentTimeMillis() - timestamp.timestamp,
                    state = BusUiState.fromDomain(timestamp.state)
                )
        }
    }
}