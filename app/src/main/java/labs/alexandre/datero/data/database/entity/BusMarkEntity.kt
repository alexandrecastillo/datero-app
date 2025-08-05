package labs.alexandre.datero.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import labs.alexandre.datero.domain.enums.BusOccupancyLevel

@Entity(tableName = "BusMarkEntity")
data class BusMarkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "busLineId")
    val busLineId: Long,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "occupancy")
    val occupancy: BusOccupancyLevel
)
