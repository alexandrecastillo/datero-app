package labs.alexandre.datero.presentation.mapper

import labs.alexandre.datero.domain.enums.BusOccupancyLevel
import labs.alexandre.datero.presentation.model.BusOccupancyUiLevel
import javax.inject.Inject

class BusOccupancyUiMapper @Inject constructor() {

    fun mapToUiModel(occupancyLevel: BusOccupancyLevel): BusOccupancyUiLevel {
        return when (occupancyLevel) {
            BusOccupancyLevel.LOW -> BusOccupancyUiLevel.LOW
            BusOccupancyLevel.MEDIUM -> BusOccupancyUiLevel.MEDIUM
            BusOccupancyLevel.HIGH -> BusOccupancyUiLevel.HIGH
        }
    }

    fun mapToDomain(uiLevel: BusOccupancyUiLevel): BusOccupancyLevel {
        return when (uiLevel) {
            BusOccupancyUiLevel.LOW -> BusOccupancyLevel.LOW
            BusOccupancyUiLevel.MEDIUM -> BusOccupancyLevel.MEDIUM
            BusOccupancyUiLevel.HIGH -> BusOccupancyLevel.HIGH
        }
    }

}