package labs.alexandre.datero.presentation.features.dashboard.intent

import labs.alexandre.datero.presentation.base.intent.BaseIntent
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.model.BusOccupancyUiLevel

sealed interface DashboardIntent : BaseIntent {
    data class OnClickMarkTimestampBusLine(val busLine: BusLineUiModel) : DashboardIntent
    data class OnSelectAnswer(val answer: BusOccupancyUiLevel) : DashboardIntent
    object OnDismissMarkTimestampBusLine : DashboardIntent
    object OnSubmitBusMark : DashboardIntent
    object StartObservingBusLines : DashboardIntent
    object OnClickAddBusLine : DashboardIntent
    object OnClickSettings : DashboardIntent

    data class OnBusMarkProgressFinish(
        val busLineId: Long, val busMarkId: Long
    ) : DashboardIntent
}