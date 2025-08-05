package labs.alexandre.datero.domain.repository

import kotlinx.coroutines.flow.Flow
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.BusMark

interface BusLineRepository {

    fun observeBusLines(): Flow<List<BusLine>>
    fun observeBusLinesWithBusMarks(): Flow<List<BusLine>>
    suspend fun upsertBusLine(busLine: BusLine): Long
    suspend fun addBusMark(busMark: BusMark): Long
    suspend fun deleteBusLine(busLineId: Long): Boolean
    suspend fun getBusLine(busLineId: Long): BusLine?
    suspend fun reorder(busLines: List<BusLine>): Boolean

}