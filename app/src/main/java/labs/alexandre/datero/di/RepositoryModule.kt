package labs.alexandre.datero.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.core.time.TimeChangeDetector
import labs.alexandre.datero.data.database.DateroDatabase
import labs.alexandre.datero.data.database.dao.BusLineDao
import labs.alexandre.datero.data.database.dao.BusMarkDao
import labs.alexandre.datero.data.repository.BusLineRepositoryImpl
import labs.alexandre.datero.domain.repository.BusLineRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBusLineRepository(
        busLineDao: BusLineDao,
        busMarkDao: BusMarkDao,
        database: DateroDatabase,
        timeChangeDetector: TimeChangeDetector
    ): BusLineRepository = BusLineRepositoryImpl(busLineDao, busMarkDao, database, timeChangeDetector)
}
