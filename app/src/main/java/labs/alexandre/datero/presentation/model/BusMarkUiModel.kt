package labs.alexandre.datero.presentation.model

import labs.alexandre.datero.domain.model.Cycle

sealed class BusMarkUiModel {
    abstract val id: Long
    abstract val busLineId: Long
    abstract val timestamp: Long
    abstract val occupancy: BusOccupancyUiLevel
    abstract val elapsedTime: ElapsedTimeUiModel

    data class Historical(
        override val id: Long,
        override val busLineId: Long,
        override val timestamp: Long,
        override val occupancy: BusOccupancyUiLevel,
        override val elapsedTime: ElapsedTimeUiModel,
    ) : BusMarkUiModel()

    data class Current(
        override val id: Long,
        override val busLineId: Long,
        override val timestamp: Long,
        override val occupancy: BusOccupancyUiLevel,
        override val elapsedTime: ElapsedTimeUiModel,
        val cycle: Cycle
    ) : BusMarkUiModel()

}