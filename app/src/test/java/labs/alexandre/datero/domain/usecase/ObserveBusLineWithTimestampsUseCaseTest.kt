package labs.alexandre.datero.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import labs.alexandre.datero.domain.enums.BusOccupancyLevel
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.BusMark
import labs.alexandre.datero.domain.model.Cycle
import labs.alexandre.datero.domain.model.ElapsedTime
import labs.alexandre.datero.domain.model.TimeUnit
import labs.alexandre.datero.domain.repository.BusLineRepository
import org.junit.Before
import org.junit.Test

class ObserveBusLineWithTimestampsUseCaseTest {

    private lateinit var repository: BusLineRepository
    private lateinit var calculateElapsedTimeUseCase: CalculateElapsedTimeUseCase
    private lateinit var calculateCycleUseCase: CalculateCycleUseCase
    private lateinit var useCase: ObserveBusLineWithTimestampsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        calculateElapsedTimeUseCase = mockk()
        calculateCycleUseCase = mockk()
        useCase = ObserveBusLineWithTimestampsUseCase(repository, calculateElapsedTimeUseCase, calculateCycleUseCase)
    }

    @Test
    fun `given bus lines with marks when observing then marks are classified correctly`() = runTest {
        // Given
        val busLineId = 1L
        val mark1 = BusMark.Undefined(id = 1, busLineId = busLineId, timestamp = 1000L, occupancy = BusOccupancyLevel.LOW)
        val mark2 = BusMark.Undefined(id = 2, busLineId = busLineId, timestamp = 2000L, occupancy = BusOccupancyLevel.MEDIUM)

        val busLine = BusLine(id = busLineId, name = "Line 1", colors = emptyList(), marks = listOf(mark2, mark1))

        // Mock repository to emit list with one busLine
        coEvery { repository.observeBusLinesWithBusMarks() } returns flowOf(listOf(busLine))

        // Mock elapsedTime and cycle for the marks
        every { calculateElapsedTimeUseCase.invoke(any()) } returns ElapsedTime(5, TimeUnit.MINUTES)
        every { calculateCycleUseCase.invoke(any()) } returns Cycle(startSystemClock = 0L, endSystemClock = 60000L)

        // When: collect flow and check classification
        useCase.invoke().test {
            // Then
            val result = awaitItem()

            assertThat(result).hasSize(1)
            val resultMarks = result[0].marks

            // The first mark in list is BusMark.Current (because nextMark == null for first element)
            assertThat(resultMarks[0]).isInstanceOf(BusMark.Current::class.java)
            assertThat(resultMarks[0].id).isEqualTo(2)

            // The second mark should be historical
            assertThat(resultMarks[1]).isInstanceOf(BusMark.Historical::class.java)
            assertThat(resultMarks[1].id).isEqualTo(1)

            cancelAndIgnoreRemainingEvents()
        }
    }

}