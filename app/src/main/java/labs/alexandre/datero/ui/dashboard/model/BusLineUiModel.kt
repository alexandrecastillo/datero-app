package labs.alexandre.datero.ui.dashboard.model

import androidx.compose.runtime.snapshots.SnapshotStateMap
import labs.alexandre.datero.domain.model.BusLine

data class BusLineUiModel(
    val id: String,
    val name: String,
    val colors: List<String>,
    val position: Int,
    val timestamps: SnapshotStateMap<String, BusTimestampUiModel>
)

fun BusLine.toUiModel(): BusLineUiModel = BusLineUiModel(
    id = id.toString(),
    name = name,
    colors = colors,
    position = 0,
    timestamps = timestamps.toSnapshotStateMap()
)