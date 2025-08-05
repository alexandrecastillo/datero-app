package labs.alexandre.datero.presentation.features.busLines.state

import androidx.compose.runtime.Immutable
import labs.alexandre.datero.presentation.model.BusLineUiModel

@Immutable
data class BusLinesUiState(
    val contentState: ContentState,
    val busLines: List<BusLineUiModel>,
    val showConfirmDelete: Boolean,
    val busLineToDelete: BusLineUiModel?
) {
    companion object {
        val DEFAULT = BusLinesUiState(
            contentState = ContentState.IDLE,
            busLines = emptyList(),
            showConfirmDelete = false,
            busLineToDelete = null
        )
    }
}

enum class ContentState {
    IDLE, LOADED
}