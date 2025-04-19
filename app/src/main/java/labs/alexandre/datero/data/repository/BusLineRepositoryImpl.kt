package labs.alexandre.datero.data.repository

import android.icu.util.Calendar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import labs.alexandre.datero.data.event.BusLineDataEvent
import labs.alexandre.datero.domain.enums.BusState
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.Timestamp
import kotlin.random.Random

class BusLineRepositoryImpl : BusLineRepository {

    override suspend fun getBusLines(): List<BusLine> {
        return listOf(
            BusLine(
                id = 1,
                name = "La Chama",
                colors = listOf(
                    "#FFD73939",
                    "#FF196320",
                    "#FF20882B",
                    "#FFF8F8F8",
                    "#FFD73939"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, -100)
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, -200)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, -300)
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, -280)
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, -330)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, -450)
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),

            BusLine(
                id = 2,
                name = "La E",
                colors = listOf(
                    "#FFFFFFFF",
                    "#FFFFB344"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),
            BusLine(
                id = 3,
                name = "La 73",
                colors = listOf(
                    "#78C63A"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),
            BusLine(
                id = 4,
                name = "El Chino",
                colors = listOf(
                    "#01B4EC"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),
            BusLine(
                id = 5,
                name = "La Chama",
                colors = listOf(
                    "#FFD73939",
                    "#FF196320",
                    "#FF20882B",
                    "#FFF8F8F8",
                    "#FFD73939"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),
            BusLine(
                id = 6,
                name = "La E",
                colors = listOf(
                    "#FFFFFFFF",
                    "#FFFFB344"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),
            BusLine(
                id = 7,
                name = "La 73",
                colors = listOf(
                    "#78C63A"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, -300)
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, -280)
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MINUTE, -5)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, -330)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, -450)
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            ),
            BusLine(
                id = 8,
                name = "El Chino",
                colors = listOf(
                    "#01B4EC"
                ),
                timestamps = listOf(
                    Timestamp(
                        id = 1,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -30)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.VERY_LOW
                    ),
                    Timestamp(
                        id = 2,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -20)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 3,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -10)
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 4,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -32)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -10)
                        }.timeInMillis,
                        state = BusState.OVER_FULL
                    ),
                    Timestamp(
                        id = 5,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -25)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.FULL
                    ),
                    Timestamp(
                        id = 6,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -37)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.LOW
                    ),
                    Timestamp(
                        id = 7,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                    Timestamp(
                        id = 8,
                        busLineId = 1,
                        timestamp = Calendar.getInstance().apply {
                            set(Calendar.SECOND, -45)
                            set(Calendar.MILLISECOND, Random.nextInt(0, 999))
                            set(Calendar.MINUTE, -8)
                        }.timeInMillis,
                        state = BusState.NORMAL
                    ),
                )
            )
        )
    }

    override fun getBusLinesEvents(): Flow<BusLineDataEvent> {
        return flow {
            delay(5000)
            emit(
                BusLineDataEvent.BusLineAdded(
                    BusLine(
                        id = 15,
                        name = "El Chosicano",
                        colors = listOf("#FFFFFF", "#2DAF3A", "#196320"),
                        timestamps = listOf()
                    )
                )
            )
        }
    }

    override suspend fun addBusLine(busLine: BusLine) {

    }

    override suspend fun addBusTimestamp(timestamp: Timestamp) {
        TODO("Not yet implemented")
    }

}