package labs.alexandre.datero.data

import kotlinx.coroutines.flow.Flow

interface BusLineRepository {
    fun getBusLines(): Flow<String>
}