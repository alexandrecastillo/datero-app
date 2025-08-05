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
        return marks.mapIndexed { index, current ->
            val preview = marks.getOrNull(index - 1)

            when (preview) {
                null -> {
                    val elapsedTime = calculateElapsedTimeUseCase.invoke(
                        Param.Current(current.timestamp)
                    )

                    val cycle = calculateCycleUseCase.invoke(current.timestamp)

                    BusMark.Current(
                        id = current.id,
                        busLineId = current.busLineId,
                        timestamp = current.timestamp,
                        occupancy = current.occupancy,
                        elapsedTime = elapsedTime,
                        cycle = cycle
                    )
                }

                else -> {
                    val elapsedTime = calculateElapsedTimeUseCase.invoke(
                        Param.Historical(preview, current)
                    )

                    BusMark.Historical(
                        id = current.id,
                        busLineId = current.busLineId,
                        timestamp = current.timestamp,
                        occupancy = current.occupancy,
                        elapsedTime = elapsedTime
                    )
                }
            }
        }
    }


}