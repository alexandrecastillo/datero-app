package labs.alexandre.datero.presentation.mapper

import labs.alexandre.datero.domain.model.BusMark
import labs.alexandre.datero.presentation.model.BusMarkUiModel
import javax.inject.Inject

class BusMarkUiMapper @Inject constructor(
    private val elapsedTimeUiMapper: ElapsedTimeUiMapper,
    private val occupancyUiMapper: BusOccupancyUiMapper
) {

    fun mapToUiModel(mark: BusMark): BusMarkUiModel? {
        return when (mark) {
            is BusMark.Current -> {
                BusMarkUiModel.Current(
                    id = mark.id,
                    busLineId = mark.busLineId,
                    timestamp = mark.timestamp,
                    occupancy = occupancyUiMapper.mapToUiModel(mark.occupancy),
                    elapsedTime = elapsedTimeUiMapper.mapToUiModel(mark.elapsedTime),
                    cycle = mark.cycle
                )
            }

            is BusMark.Historical -> {
                BusMarkUiModel.Historical(
                    id = mark.id,
                    busLineId = mark.busLineId,
                    timestamp = mark.timestamp,
                    occupancy = occupancyUiMapper.mapToUiModel(mark.occupancy),
                    elapsedTime = elapsedTimeUiMapper.mapToUiModel(mark.elapsedTime),
                )
            }

            is BusMark.Undefined -> null
        }
    }

    fun mapToDomain(model: BusMarkUiModel): BusMark {
        return BusMark.Undefined(
            id = model.id,
            busLineId = model.busLineId,
            timestamp = model.timestamp,
            occupancy = occupancyUiMapper.mapToDomain(model.occupancy)
        )
    }

}
