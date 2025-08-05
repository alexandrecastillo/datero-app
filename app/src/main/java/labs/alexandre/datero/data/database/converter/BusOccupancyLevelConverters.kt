package labs.alexandre.datero.data.database.converter

import androidx.room.TypeConverter
import labs.alexandre.datero.domain.enums.BusOccupancyLevel

class BusOccupancyLevelConverters {

    @TypeConverter
    fun fromOccupancy(value: BusOccupancyLevel): String = value.name

    @TypeConverter
    fun toOccupancy(value: String): BusOccupancyLevel = BusOccupancyLevel.valueOf(value)

}