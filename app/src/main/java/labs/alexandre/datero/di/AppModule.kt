package labs.alexandre.datero.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.core.resources.DefaultStringProvider
import labs.alexandre.datero.core.resources.StringProvider
import labs.alexandre.datero.core.time.TimeChangeDetector
import labs.alexandre.datero.core.time.TimeChangeDetectorAndroid

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindStringProvider(
        stringProvider: DefaultStringProvider
    ): StringProvider

    @Binds
    abstract fun bindTimeChangeDetector(
        timeChangeDetector: TimeChangeDetectorAndroid
    ): TimeChangeDetector

}
