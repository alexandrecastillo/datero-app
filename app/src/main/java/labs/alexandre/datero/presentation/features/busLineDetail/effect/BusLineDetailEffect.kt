package labs.alexandre.datero.presentation.features.busLineDetail.effect

sealed interface BusLineDetailEffect {
    object NavigateToBack : BusLineDetailEffect
    data class ShowMessage(val message: String) : BusLineDetailEffect
    object PerformHapticLongPress: BusLineDetailEffect
    object PerformHapticGestureEnd: BusLineDetailEffect

}