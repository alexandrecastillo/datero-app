package labs.alexandre.datero.platform

import android.os.SystemClock
import labs.alexandre.datero.domain.provider.SystemClockProvider
import javax.inject.Inject

class SystemClockProviderImpl @Inject constructor(): SystemClockProvider {

    override fun elapsedRealtime(): Long {
        return SystemClock.elapsedRealtime()
    }

}