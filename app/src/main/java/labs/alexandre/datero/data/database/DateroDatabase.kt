package labs.alexandre.datero.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import labs.alexandre.datero.data.database.converter.BusOccupancyLevelConverters
import labs.alexandre.datero.data.database.converter.StringConverter
import labs.alexandre.datero.data.database.dao.BusLineDao
import labs.alexandre.datero.data.database.dao.BusMarkDao
import labs.alexandre.datero.data.database.entity.BusLineEntity
import labs.alexandre.datero.data.database.entity.BusMarkEntity

@Database(
    entities = [
        BusLineEntity::class,
        BusMarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringConverter::class, BusOccupancyLevelConverters::class)
abstract class DateroDatabase : RoomDatabase() {
    abstract fun busLineDao(): BusLineDao
    abstract fun busMarkDao(): BusMarkDao
}