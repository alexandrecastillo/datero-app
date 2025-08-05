package labs.alexandre.datero.presentation.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import labs.alexandre.datero.core.time.TimeChangeDetector
import javax.inject.Inject

class AppLifecycleManager @Inject constructor(
    private val timeChangeDetector: TimeChangeDetector
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        timeChangeDetector.register()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        timeChangeDetector.unregister()
    }

}