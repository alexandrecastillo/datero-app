package labs.alexandre.datero.presentation.features.busLines.intent

import labs.alexandre.datero.presentation.model.BusLineUiModel

sealed interface BusLinesIntent {
    object OnClickClose : BusLinesIntent
    object OnClickAddBusLine : BusLinesIntent
    data class OnClickDeleteBusLine(val busLine: BusLineUiModel) : BusLinesIntent
    object OnDismissDialogConfirmDelete : BusLinesIntent
    object OnSubmitConfirmDelete : BusLinesIntent
    data class OnMoveBusLineItem(val fromId: Long, val toId: Long) : BusLinesIntent
    data class OnClickBusLine(val busLineId: Long) : BusLinesIntent
    object OnDragEndBusLines : BusLinesIntent
    object StartObservingBusLines : BusLinesIntent
}