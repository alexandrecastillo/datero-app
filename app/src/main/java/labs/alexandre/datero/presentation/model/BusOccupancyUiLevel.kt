package labs.alexandre.datero.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.theme.FullColor
import labs.alexandre.datero.presentation.theme.LowColor
import labs.alexandre.datero.presentation.theme.MediumColor

enum class BusOccupancyUiLevel(
    val color: Color,
    @DrawableRes
    val iconRes: Int,
    @StringRes
    val labelRes: Int
) {
    LOW(LowColor, R.drawable.ic_bus_low, R.string.dashboard_dialog_mark_timestamp_low_occupancy),
    MEDIUM(MediumColor, R.drawable.ic_bus_neutral, R.string.dashboard_dialog_mark_timestamp_medium_occupancy),
    HIGH(FullColor, R.drawable.ic_bus_full, R.string.dashboard_dialog_mark_timestamp_high_occupancy)
}