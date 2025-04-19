package labs.alexandre.datero.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.manager.TimestampSnapshotManager

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BootReceiverEntryPoint {
    fun provideTimestampSnapshotManager(): TimestampSnapshotManager
}