package labs.alexandre.datero.presentation.mapper

import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.presentation.model.BusLineUiModel
import javax.inject.Inject

class BusLineUiMapper @Inject constructor(
    private val busMarkUiMapper: BusMarkUiMapper
) {

    fun mapToUiModel(busLine: BusLine): BusLineUiModel {
        return BusLineUiModel(
            id = busLine.id ?: -1,
            name = busLine.name,
            colors = busLine.colors,
            position = busLine.position,
            marks = busLine.marks.mapNotNull { busMarkUiMapper.mapToUiModel(it) }
        )
    }

    fun mapToDomain(model: BusLineUiModel): BusLine {
        return BusLine(
            id = model.id.takeIf { it != -1L },
            name = model.name,
            colors = model.colors,
            position = model.position,
            marks = model.marks.map { busMarkUiMapper.mapToDomain(it) }
        )
    }

}