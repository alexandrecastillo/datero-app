package labs.alexandre.datero.presentation.features.dashboard.effect

import labs.alexandre.datero.presentation.base.intent.BaseEffect

sealed interface DashboardEffect : BaseEffect {
    object NavigateToSettings : DashboardEffect
    object NavigateToCreateBusLine : DashboardEffect
    data class ShowSnackbar(val message: String) : DashboardEffect
}