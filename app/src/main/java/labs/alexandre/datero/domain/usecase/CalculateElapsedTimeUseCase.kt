package labs.alexandre.datero.domain.usecase

import labs.alexandre.datero.domain.constants.BusinessRules
import labs.alexandre.datero.domain.constants.Times
import labs.alexandre.datero.domain.model.ElapsedTime
import labs.alexandre.datero.domain.model.TimeUnit
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import javax.inject.Inject

class CalculateElapsedTimeUseCase @Inject constructor(
    private val systemTimeProvider: SystemTimeProvider
) {

    fun invoke(param: Param): ElapsedTime {
        val elapsedTimeInMs = getElapsedTimeInMs(param)
        val adjustedElapsedTime = getAdjustedElapsedTime(elapsedTimeInMs)
        return calculate(adjustedElapsedTime)
    }

    private fun getElapsedTimeInMs(param: Param): Long {
        return when (param) {
            is Param.Ongoing -> {
                systemTimeProvider.getCurrentTime() - param.busMarkTimestamp
            }

            is Param.BetweenMarks -> {
                param.nextMarkTimestamp - param.referenceMarkTimestamp
            }
        }
    }

    private fun getAdjustedElapsedTime(elapsedTimeInMs: Long): Long {
        return elapsedTimeInMs + BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS
    }

    private fun calculate(adjustedElapsedTime: Long): ElapsedTime {
        return when {
            adjustedElapsedTime < Times.ONE_MINUTE_IN_MS -> {
                ElapsedTime(Times.ZERO, TimeUnit.MINUTES)
            }

            adjustedElapsedTime < Times.ONE_HOUR_IN_MS -> {
                val minutes = (adjustedElapsedTime / Times.ONE_MINUTE_IN_MS).toInt()
                ElapsedTime(minutes, TimeUnit.MINUTES)
            }

            else -> {
                val hours = (adjustedElapsedTime / Times.ONE_HOUR_IN_MS).toInt()
                ElapsedTime(hours, TimeUnit.HOURS)
            }
        }
    }

}

sealed interface Param {

    data class BetweenMarks(
        val nextMarkTimestamp: Long,
        val referenceMarkTimestamp: Long
    ) : Param

    data class Ongoing(
        val busMarkTimestamp: Long
    ) : Param

}