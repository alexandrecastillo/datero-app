package labs.alexandre.datero

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import labs.alexandre.datero.presentation.lifecycle.AppLifecycleManager
import javax.inject.Inject

@HiltAndroidApp
class DateroApp : Application() {

    @Inject
    lateinit var appLifecycleManager: AppLifecycleManager

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleManager)
    }

}
