package labs.alexandre.datero.data.event

import labs.alexandre.datero.domain.model.BusLine

sealed class BusLineDataEvent {

    data class BusLineAdded(
        val busLine: BusLine
    ) : BusLineDataEvent()

    data class TimestampAdded(
        val busLineId: Long,
        val timestampId: Long,
        val timestamp: Long
    ) : BusLineDataEvent()

}
