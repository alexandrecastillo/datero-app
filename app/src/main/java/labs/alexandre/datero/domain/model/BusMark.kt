package labs.alexandre.datero.domain.model

import labs.alexandre.datero.domain.enums.BusOccupancyLevel

sealed class BusMark {
    abstract val id: Long
    abstract val busLineId: Long
    abstract val timestamp: Long
    abstract val occupancy: BusOccupancyLevel

    data class Undefined(
        override val id: Long,
        override val busLineId: Long,
        override val timestamp: Long,
        override val occupancy: BusOccupancyLevel,
    ) : BusMark()

    data class Current(
        override val id: Long,
        override val busLineId: Long,
        override val timestamp: Long,
        override val occupancy: BusOccupancyLevel,
        val elapsedTime: ElapsedTime,
        val cycle: Cycle
    ) : BusMark()

    data class Historical(
        override val id: Long,
        override val busLineId: Long,
        override val timestamp: Long,
        override val occupancy: BusOccupancyLevel,
        val elapsedTime: ElapsedTime
    ) : BusMark()
}