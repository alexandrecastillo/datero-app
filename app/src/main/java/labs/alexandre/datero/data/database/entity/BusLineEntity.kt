package labs.alexandre.datero.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BusLineEntity")
data class BusLineEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "colors")
    val colors: List<String>,
    @ColumnInfo(name = "position")
    val position: Int
)