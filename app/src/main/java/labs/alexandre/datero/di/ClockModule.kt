package labs.alexandre.datero.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.domain.provider.SystemClockProvider
import labs.alexandre.datero.platform.SystemClockProviderImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ClockModule {

    @Binds
    abstract fun bindSystemClockProvider(
        systemClockProvider: SystemClockProviderImpl
    ): SystemClockProvider

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ClockEntryPoint {
    fun systemClockProvider(): SystemClockProvider
}

fun provideSystemClockProvider(context: Context): SystemClockProvider {
    val appContext = context.applicationContext
    val entryPoint = EntryPointAccessors.fromApplication(appContext, ClockEntryPoint::class.java)
    return entryPoint.systemClockProvider()
}