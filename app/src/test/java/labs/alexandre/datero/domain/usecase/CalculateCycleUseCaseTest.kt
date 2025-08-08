package labs.alexandre.datero.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import labs.alexandre.datero.domain.constants.BusinessRules
import labs.alexandre.datero.domain.constants.Times
import labs.alexandre.datero.domain.provider.SystemClockProvider
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import labs.alexandre.datero.util.fixedTimestamp
import org.junit.Before
import org.junit.Test

class CalculateCycleUseCaseTest {

    private lateinit var systemClockProvider: SystemClockProvider
    private lateinit var systemTimeProvider: SystemTimeProvider
    private lateinit var useCase: CalculateCycleUseCase

    @Before
    fun setUp() {
        systemClockProvider = mockk()
        systemTimeProvider = mockk()
        useCase = CalculateCycleUseCase(
            systemClockProvider = systemClockProvider,
            systemTimeProvider = systemTimeProvider
        )
    }

    @Test
    fun `given mark minor than one minute minus margin when calculating cycle then returns one minute minus margin`() {
        // given
        val markTime = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val currentTime = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 30) // +30s
        val systemClockNow = 5_000L

        every { systemTimeProvider.getCurrentTime() } returns currentTime
        every { systemClockProvider.elapsedRealtime() } returns systemClockNow

        // when
        val cycle = useCase.invoke(markTime)

        // then
        val actualDuration = cycle.endSystemClock - cycle.startSystemClock
        val expectedDuration = Times.ONE_MINUTE_IN_MS - BusinessRules.MARGIN_TO_NEXT_MINUTE_IN_MS

        assertThat(actualDuration).isEqualTo(expectedDuration)
    }

    @Test
    fun `given non recent mark under one hour when calculating cycle then returns one minute`() {
        // Given
        val markTime = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val currentTime = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 30, second = 0)
        val systemClockNow = 3_000L

        every { systemTimeProvider.getCurrentTime() } returns currentTime
        every { systemClockProvider.elapsedRealtime() } returns systemClockNow

        // When
        val cycle = useCase.invoke(markTime)

        // Then
        val actualDuration = cycle.endSystemClock - cycle.startSystemClock
        val expectedDuration = Times.ONE_MINUTE_IN_MS

        assertThat(actualDuration).isEqualTo(expectedDuration)
    }


    @Test
    fun `given mark after one hour or more when calculating cycle then returns one hour cycle`() {
        // Given
        val markTime = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val currentTime = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 11, minute = 0, second = 0) // +1h
        val systemClockNow = 7_000L

        every { systemTimeProvider.getCurrentTime() } returns currentTime
        every { systemClockProvider.elapsedRealtime() } returns systemClockNow

        // When
        val cycle = useCase.invoke(markTime)

        // Then
        val actualDuration = cycle.endSystemClock - cycle.startSystemClock
        val expectedDuration = Times.ONE_HOUR_IN_MS // 1 hour
        assertThat(actualDuration).isEqualTo(expectedDuration)
    }

}