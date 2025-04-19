package labs.alexandre.datero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import labs.alexandre.datero.di.BootReceiverEntryPoint
import labs.alexandre.datero.manager.TimestampSnapshotManager
import labs.alexandre.datero.ui.util.getProvidesFromEntryPoint

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val provides = context.getProvidesFromEntryPoint(BootReceiverEntryPoint::class.java)
            saveBoot(provides.provideTimestampSnapshotManager())
        }
    }

    fun saveBoot(timestampSnapshotManager: TimestampSnapshotManager) {
        CoroutineScope(Dispatchers.IO).launch {
            timestampSnapshotManager.apply {
                setElapsedSinceBoot(SystemClock.elapsedRealtime())
                setSystemTimestampBoot(System.currentTimeMillis())
            }
        }
    }

}