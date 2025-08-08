package labs.alexandre.datero.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.BusMark
import labs.alexandre.datero.domain.repository.BusLineRepository
import javax.inject.Inject

class ObserveBusLineWithTimestampsUseCase @Inject constructor(
    private val busLineRepository: BusLineRepository,
    private val calculateElapsedTimeUseCase: CalculateElapsedTimeUseCase,
    private val calculateCycleUseCase: CalculateCycleUseCase
) {

    operator fun invoke(): Flow<List<BusLine>> {
        return busLineRepository.observeBusLinesWithBusMarks().map { busLines ->
            busLines.map { busLine ->
                busLine.copy(marks = classifyMarks(busLine.marks))
            }
        }
    }

    fun classifyMarks(
        marks: List<BusMark>,
    ): List<BusMark> {
        return marks.mapIndexed { index, reference ->
            val nextMark = marks.getOrNull(index - 1)

            when (nextMark) {
                null -> {
                    val elapsedTime = calculateElapsedTimeUseCase.invoke(
                        Param.Ongoing(reference.timestamp)
                    )

                    val cycle = calculateCycleUseCase.invoke(reference.timestamp)

                    BusMark.Current(
                        id = reference.id,
                        busLineId = reference.busLineId,
                        timestamp = reference.timestamp,
                        occupancy = reference.occupancy,
                        elapsedTime = elapsedTime,
                        cycle = cycle
                    )
                }

                else -> {
                    val elapsedTime = calculateElapsedTimeUseCase.invoke(
                        Param.BetweenMarks(nextMark.timestamp, reference.timestamp)
                    )

                    BusMark.Historical(
                        id = reference.id,
                        busLineId = reference.busLineId,
                        timestamp = reference.timestamp,
                        occupancy = reference.occupancy,
                        elapsedTime = elapsedTime
                    )
                }
            }
        }
    }


}