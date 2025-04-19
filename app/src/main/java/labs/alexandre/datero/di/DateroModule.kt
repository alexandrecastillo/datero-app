package labs.alexandre.datero.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.data.repository.BusLineRepository
import labs.alexandre.datero.data.repository.BusLineRepositoryImpl
import labs.alexandre.datero.manager.BusLinesManager
import labs.alexandre.datero.manager.TimestampSnapshotManager
import labs.alexandre.datero.tracker.BusTimestampTracker

@Module
@InstallIn(SingletonComponent::class)
class DateroModule {

    @Provides
    fun provideBusLineRepository(): BusLineRepository {
        return BusLineRepositoryImpl()
    }

    @Provides
    fun provideTracker(
        timestampSnapshotManager: TimestampSnapshotManager
    ): BusTimestampTracker {
        return BusTimestampTracker(timestampSnapshotManager)
    }

    @Provides
    fun provideTimestampSnapshotDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("timestamp-boot")
            }
        )
    }

    @Provides
    fun provideTimestampSnapshotManager(
        datastorePreferences: DataStore<Preferences>
    ): TimestampSnapshotManager {
        return TimestampSnapshotManager(datastorePreferences)
    }

    @Provides
    fun provideBusLinesManager(
        busLineRepository: BusLineRepository,
        tracker: BusTimestampTracker
    ): BusLinesManager {
        return BusLinesManager(busLineRepository, tracker)
    }

}