package labs.alexandre.datero.domain.model

import labs.alexandre.datero.domain.enums.BusState

data class Timestamp(
    val id: Long,
    val busLineId: Long,
    val timestamp: Long,
    val state: BusState
)
