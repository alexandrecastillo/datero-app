package labs.alexandre.datero.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import labs.alexandre.datero.platform.SystemTimeProviderImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {

    @Binds
    abstract fun bindSystemTimeProvider(
        systemTimeProvider: SystemTimeProviderImpl
    ): SystemTimeProvider
}