package labs.alexandre.datero.presentation.features.dashboard.state

import androidx.compose.runtime.Immutable
import labs.alexandre.datero.presentation.base.intent.BaseState
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.model.BusOccupancyUiLevel

@Immutable
data class DashboardUiState(
    val contentState: DashboardContentState,
    val busLines: List<BusLineUiModel>,
    val markTimestampState: MarkTimestampUiState
): BaseState {
    companion object {
        val DEFAULT = DashboardUiState(
            contentState = DashboardContentState.IDLE,
            busLines = emptyList(),
            markTimestampState = MarkTimestampUiState.DEFAULT
        )
    }
}

@Immutable
data class MarkTimestampUiState(
    val showDialog: Boolean,
    val occupancy: BusOccupancyUiLevel?,
    val busLine: BusLineUiModel
) {
    companion object {
        val DEFAULT = MarkTimestampUiState(
            showDialog = false,
            occupancy = null,
            busLine = BusLineUiModel.Companion.EMPTY
        )
    }
}

enum class DashboardContentState {
    IDLE, LOADING, LOADED
}