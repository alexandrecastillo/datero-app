package labs.alexandre.datero.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import labs.alexandre.datero.data.database.entity.BusMarkEntity

@Dao
interface BusMarkDao {

    @Query("""
        SELECT * FROM BusMarkEntity
        WHERE busLineId = :busLineId
        AND date(timestamp / 1000, 'unixepoch', 'localtime') = date('now', 'localtime')
        ORDER BY timestamp DESC 
        LIMIT 9
        """)
    fun observeLast9BusMarks(busLineId: Long): Flow<List<BusMarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timestamp: BusMarkEntity): Long

    @Query("DELETE FROM BusMarkEntity WHERE busLineId = :busLineId")
    suspend fun deleteBusMarksByBusLineId(busLineId: Long)

}