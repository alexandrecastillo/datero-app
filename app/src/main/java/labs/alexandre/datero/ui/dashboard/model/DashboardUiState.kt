package labs.alexandre.datero.ui.dashboard.model

sealed class DashboardUiState {
    data object Idle: DashboardUiState()
    data object Loading: DashboardUiState()
    data object Loaded: DashboardUiState()
}
