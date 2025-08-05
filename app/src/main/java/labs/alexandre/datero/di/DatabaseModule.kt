package labs.alexandre.datero.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.data.database.DateroDatabase
import labs.alexandre.datero.data.database.dao.BusLineDao
import labs.alexandre.datero.data.database.dao.BusMarkDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DateroDatabase = Room
        .databaseBuilder(context, DateroDatabase::class.java, "datero.db")
        .fallbackToDestructiveMigration(false)
        .build()

    @Provides
    fun provideBusLineDao(database: DateroDatabase): BusLineDao = database.busLineDao()

    @Provides
    fun provideBusMarkDao(database: DateroDatabase): BusMarkDao = database.busMarkDao()
}
