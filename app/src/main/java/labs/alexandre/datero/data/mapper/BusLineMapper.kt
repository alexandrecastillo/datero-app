package labs.alexandre.datero.data.mapper

import labs.alexandre.datero.data.database.entity.BusLineEntity
import labs.alexandre.datero.data.database.entity.BusMarkEntity
import labs.alexandre.datero.domain.model.BusLine

fun BusLineEntity.toDomain(marks: List<BusMarkEntity> = emptyList()): BusLine = BusLine(
    id = id,
    name = name,
    colors = colors,
    position = position,
    marks = marks.map { it.toDomain() }
)

fun BusLine.toData(): BusLineEntity = BusLineEntity(
    id = id ?: 0,
    name = name,
    colors = colors,
    position = position,
)
