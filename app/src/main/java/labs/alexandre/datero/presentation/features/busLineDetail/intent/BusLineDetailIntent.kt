package labs.alexandre.datero.presentation.features.busLineDetail.intent

sealed interface BusLineDetailIntent {
    data class OnChangeName(val value: String) : BusLineDetailIntent
    data class OnSaveColor(val colorId: String?, val colorHex: String) : BusLineDetailIntent
    data class OnMoveColor(val fromId: String, val toId: String) : BusLineDetailIntent
    data class OnDeleteStrip(val colorId: String) : BusLineDetailIntent
    data class OnClickColor(val colorId: String) : BusLineDetailIntent
    data class OnDuplicateColor(val colorId: String) : BusLineDetailIntent
    object OnColorDragStarted : BusLineDetailIntent
    object OnColorDragStopped : BusLineDetailIntent
    object OnSubmitSave : BusLineDetailIntent
    object OnClickAddColor : BusLineDetailIntent
    object OnDismissColorPicker : BusLineDetailIntent
    object OnCloseClick : BusLineDetailIntent
    object OnBackScreen : BusLineDetailIntent
    object OnDismissDiscardChangesDialog : BusLineDetailIntent
    object OnSubmitDiscardChangesDialog : BusLineDetailIntent

}