package labs.alexandre.datero.ui.dashboard.model

import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.graphics.Color

data class BusLineUiModel(
    val id: String,
    val name: String,
    val colors: List<Color>,
    val timestamps: SnapshotStateMap<String, BusTimestampUiModel>
)