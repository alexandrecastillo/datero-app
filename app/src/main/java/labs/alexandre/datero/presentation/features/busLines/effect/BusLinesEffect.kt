package labs.alexandre.datero.presentation.features.busLines.effect

sealed interface BusLinesEffect {
    object NavigateToBack : BusLinesEffect
    data class NavigateToBusLineDetail(val busLineId: Long? = null) : BusLinesEffect
}