package labs.alexandre.datero.core.time

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeChangeDetectorAndroid @Inject constructor(
    @ApplicationContext
    private val context: Context
) : TimeChangeDetector {

    private val _events = MutableSharedFlow<TimeChangeEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val events: SharedFlow<TimeChangeEvent> = _events

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val reason = when (intent?.action) {
                Intent.ACTION_TIME_CHANGED -> TimeChangeEvent.TIME_CHANGED
                Intent.ACTION_TIMEZONE_CHANGED -> TimeChangeEvent.TIMEZONE_CHANGED
                Intent.ACTION_DATE_CHANGED -> TimeChangeEvent.DATE_CHANGED
                Intent.ACTION_TIME_TICK -> TimeChangeEvent.TIME_TICK
                else -> null
            }

            reason?.let {
                _events.tryEmit(it)
            }
        }
    }

    override fun register() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_DATE_CHANGED)
            addAction(Intent.ACTION_TIME_TICK)
        }
        context.registerReceiver(receiver, filter)
    }

    override fun unregister() {
        context.unregisterReceiver(receiver)
    }

}
