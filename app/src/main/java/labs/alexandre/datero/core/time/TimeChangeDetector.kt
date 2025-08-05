package labs.alexandre.datero.core.time

import kotlinx.coroutines.flow.SharedFlow

interface TimeChangeDetector {
    val events: SharedFlow<TimeChangeEvent>
    fun register()
    fun unregister()
}