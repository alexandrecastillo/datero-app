package labs.alexandre.datero.domain.usecase

import labs.alexandre.datero.domain.constants.BusinessRules
import labs.alexandre.datero.domain.constants.Times
import labs.alexandre.datero.domain.model.Cycle
import labs.alexandre.datero.domain.provider.SystemClockProvider
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import javax.inject.Inject

class CalculateCycleUseCase @Inject constructor(
    private val systemClockProvider: SystemClockProvider,
    private val systemTimeProvider: SystemTimeProvider
) {

    fun invoke(busMarkTimestamp: Long): Cycle {
        val elapsedSinceMark = getElapsedTimeFromBusMark(busMarkTimestamp)
        val cycleDuration = getCycleDuration(elapsedSinceMark)
        val elapsedInCycle = getElapsedInCycle(elapsedSinceMark, cycleDuration)
        val remainingInCycle = (cycleDuration - elapsedInCycle)
        val scpElapsedRealtime = systemClockProvider.elapsedRealtime()

        return Cycle(
            startSystemClock = scpElapsedRealtime - elapsedInCycle,
            endSystemClock = scpElapsedRealtime + remainingInCycle
        )
    }

    private fun getElapsedTimeFromBusMark(busMarkTimestamp: Long): Long {
        val now = systemTimeProvider.getCurrentTime()
        return (now - busMarkTimestamp).coerceAtLeast(0)
    }

    private fun getCycleDuration(elapsed: Long): Int {
        return when {
            (elapsed < Times.ONE_MINUTE_IN_MS - BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS) -> {
                Times.ONE_MINUTE_IN_MS - BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS
            }

            else -> {
                val adjusted = elapsed + BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS
                if (adjusted < Times.ONE_HOUR_IN_MS) Times.ONE_MINUTE_IN_MS else Times.ONE_HOUR_IN_MS
            }
        }
    }

    private fun getElapsedInCycle(elapsed: Long, duration: Int): Long {
        return if (elapsed < Times.ONE_MINUTE_IN_MS - BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS) {
            elapsed
        } else {
            val adjusted = elapsed + BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS
            (adjusted % duration).coerceAtLeast(0)
        }
    }

}
