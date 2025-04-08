package labs.alexandre.datero.ui.dashboard.model

data class BusTimestampUiModel(
    val id: String,
    val elapsedTime: Long,
    val state: BusUiState
)