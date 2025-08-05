package labs.alexandre.datero.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import labs.alexandre.datero.core.time.TimeChangeDetector
import labs.alexandre.datero.core.time.TimeChangeEvent
import labs.alexandre.datero.data.database.DateroDatabase
import labs.alexandre.datero.data.database.dao.BusLineDao
import labs.alexandre.datero.data.database.dao.BusMarkDao
import labs.alexandre.datero.data.mapper.toData
import labs.alexandre.datero.data.mapper.toDomain
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.BusMark
import labs.alexandre.datero.domain.repository.BusLineRepository
import javax.inject.Inject

class BusLineRepositoryImpl @Inject constructor(
    private val busLineDao: BusLineDao,
    private val busMarkDao: BusMarkDao,
    private val database: DateroDatabase,
    private val timeChangeDetector: TimeChangeDetector
) : BusLineRepository {

    override fun observeBusLines(): Flow<List<BusLine>> {
        return busLineDao.observeBusLines().map { it.map { it.toDomain() } }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeBusLinesWithBusMarks(): Flow<List<BusLine>> {
        return timeChangeDetector.events
            .onStart {
                emit(TimeChangeEvent.TIME_TICK)
            }
            .flatMapLatest {
                busLineDao.observeBusLines()
            }
            .flatMapLatest { busLines ->
                if (busLines.isEmpty()) {
                    return@flatMapLatest flowOf(emptyList())
                }

                val busLinesWithMarks = busLines.map { busLine ->
                    busMarkDao.observeLast9BusMarks(busLine.id)
                        .map { timestamps ->
                            busLine.toDomain(timestamps)
                        }
                }

                combine(busLinesWithMarks) { it.toList() }
            }.distinctUntilChanged()

    }

    override suspend fun upsertBusLine(busLine: BusLine): Long = withContext(Dispatchers.IO) {
        val busLineWithPosition = when (busLine.id == null) {
            true -> {
                val nextPosition = (busLineDao.getMaxPosition() ?: 0) + 1
                busLine.copy(position = nextPosition)
            }

            false -> busLine
        }

        busLineDao.upsert(busLineWithPosition.toData())
    }


    override suspend fun addBusMark(busMark: BusMark): Long = withContext(Dispatchers.IO) {
        busMarkDao.insert(busMark.toData())
    }

    override suspend fun deleteBusLine(busLineId: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext database.withTransaction {
            busLineDao.deleteById(busLineId)
            busMarkDao.deleteBusMarksByBusLineId(busLineId)
            true
        }
    }

    override suspend fun getBusLine(busLineId: Long): BusLine? = withContext(Dispatchers.IO) {
        busLineDao.getById(busLineId)?.toDomain()
    }

    override suspend fun reorder(busLines: List<BusLine>): Boolean = withContext(Dispatchers.IO) {
        val entities = busLines.map { busLine -> busLine.toData() }

        return@withContext database.withTransaction {
            busLineDao.updateAll(entities)
            true
        }
    }

}