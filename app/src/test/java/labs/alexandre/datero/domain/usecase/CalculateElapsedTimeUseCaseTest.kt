package labs.alexandre.datero.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import labs.alexandre.datero.domain.model.ElapsedTime
import labs.alexandre.datero.domain.model.TimeUnit
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import labs.alexandre.datero.util.fixedTimestamp
import org.junit.Before
import org.junit.Test

class CalculateElapsedTimeUseCaseTest {

    private lateinit var systemTimeProvider: SystemTimeProvider
    private lateinit var useCase: CalculateElapsedTimeUseCase

    @Before
    fun setUp() {
        systemTimeProvider = mockk()
        useCase = CalculateElapsedTimeUseCase(systemTimeProvider)
    }

    @Test
    fun `given ongoing time under one minute when calculating elapsed time then returns zero minutes`() {
        // Given
        val busMarkTimestamp = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val currentTimestamp = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 30) // after 30s

        every { systemTimeProvider.getCurrentTime() } returns currentTimestamp

        // When
        val result = useCase.invoke(Param.Ongoing(busMarkTimestamp))

        // Then
        assertThat(result.unit).isEqualTo(TimeUnit.MINUTES)
        assertThat(result.time).isEqualTo(0)
    }

    @Test
    fun `given ongoing time between one minute and one hour when calculating elapsed time then returns minutes`() {
        // Given
        val busMarkTimestamp = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val currentTimestamp = fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 45, second = 0) // after 45m

        every { systemTimeProvider.getCurrentTime() } returns currentTimestamp

        // When
        val result = useCase.invoke(Param.Ongoing(busMarkTimestamp))

        // Then
        assertThat(result.unit).isEqualTo(TimeUnit.MINUTES)
        assertThat(result.time).isIn(1..59)
    }

    @Test
    fun `given ongoing time over one hour when calculating elapsed time then returns hours greater than zero`() {
        // Given
        val busMarkTimestamp =
            fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val currentTimestamp =
            fixedTimestamp(year = 2025, month = 8, day = 7, hour = 12, minute = 0, second = 0) // after 2h

        every { systemTimeProvider.getCurrentTime() } returns currentTimestamp

        // When
        val result = useCase.invoke(Param.Ongoing(busMarkTimestamp))

        // Then
        assertThat(result.unit).isEqualTo(TimeUnit.HOURS)
        assertThat(result.time).isGreaterThan(0)
    }

    @Test
    fun `given BetweenMarks separated by 40 seconds when calculating elapsed time then rounds up to 1 minute`() {
        // Given
        val referenceMarkTimestamp =
            fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 0)
        val nextMarkTimestamp =
            fixedTimestamp(year = 2025, month = 8, day = 7, hour = 10, minute = 0, second = 40) // after 40s

        // When
        val param = Param.BetweenMarks(
            nextMarkTimestamp = nextMarkTimestamp,
            referenceMarkTimestamp = referenceMarkTimestamp
        )
        val result = useCase.invoke(param)

        // Then
        val expected = ElapsedTime(1, TimeUnit.MINUTES)
        assertThat(result).isEqualTo(expected)
    }

}
