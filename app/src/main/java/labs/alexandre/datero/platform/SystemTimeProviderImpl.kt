package labs.alexandre.datero.platform

import kotlinx.datetime.Clock
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import javax.inject.Inject

class SystemTimeProviderImpl @Inject constructor(): SystemTimeProvider {

    override fun getCurrentTime(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

}