package labs.alexandre.datero.ui.dashboard.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import labs.alexandre.datero.R
import labs.alexandre.datero.domain.enums.BusState
import labs.alexandre.datero.ui.theme.FullColor
import labs.alexandre.datero.ui.theme.LowColor
import labs.alexandre.datero.ui.theme.NormalColor
import labs.alexandre.datero.ui.theme.OverFullColor
import labs.alexandre.datero.ui.theme.VeryLowColor

enum class BusUiState(
    val color: Color,
    @DrawableRes
    val iconRes: Int
) {
    OVER_FULL(OverFullColor, R.drawable.ic_bus_over_full),
    FULL(FullColor, R.drawable.ic_bus_full),
    NORMAL(NormalColor, R.drawable.ic_bus_normal),
    LOW(LowColor, R.drawable.ic_bus_low),
    VERY_LOW(VeryLowColor, R.drawable.ic_bus_very_low);

    companion object {
        fun fromDomain(busState: BusState): BusUiState {
            return when (busState) {
                BusState.OVER_FULL -> OVER_FULL
                BusState.FULL -> FULL
                BusState.NORMAL -> NORMAL
                BusState.LOW -> LOW
                BusState.VERY_LOW -> VERY_LOW
            }
        }
    }
}