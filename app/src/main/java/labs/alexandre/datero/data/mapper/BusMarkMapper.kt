package labs.alexandre.datero.data.mapper

import labs.alexandre.datero.data.database.entity.BusMarkEntity
import labs.alexandre.datero.domain.model.BusMark

fun BusMarkEntity.toDomain(): BusMark.Undefined = BusMark.Undefined(
    id = id,
    busLineId = busLineId,
    timestamp = timestamp,
    occupancy = occupancy
)

fun BusMark.toData(): BusMarkEntity = BusMarkEntity(
    id = id,
    busLineId = busLineId,
    timestamp = timestamp,
    occupancy = occupancy
)
