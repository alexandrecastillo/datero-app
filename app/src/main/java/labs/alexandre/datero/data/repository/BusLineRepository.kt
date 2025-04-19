package labs.alexandre.datero.data.repository

import kotlinx.coroutines.flow.Flow
import labs.alexandre.datero.data.event.BusLineDataEvent
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.Timestamp

interface BusLineRepository {

    suspend fun getBusLines(): List<BusLine>
    fun getBusLinesEvents(): Flow<BusLineDataEvent>
    suspend fun addBusLine(busLine: BusLine)
    suspend fun addBusTimestamp(timestamp: Timestamp)

}