package labs.alexandre.datero.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import labs.alexandre.datero.data.database.entity.BusLineEntity

@Dao
interface BusLineDao {

    @Query("SELECT * FROM BusLineEntity ORDER BY position ASC")
    fun observeBusLines(): Flow<List<BusLineEntity>>

    @Upsert
    suspend fun upsert(entity: BusLineEntity): Long

    @Query("DELETE FROM BusLineEntity WHERE id = :busLineId")
    suspend fun deleteById(busLineId: Long)

    @Query("SELECT * FROM BusLineEntity WHERE id = :busLineId")
    suspend fun getById(busLineId: Long): BusLineEntity?

    @Update
    suspend fun updateAll(busLines: List<BusLineEntity>)

    @Query("SELECT MAX(position) FROM BusLineEntity")
    suspend fun getMaxPosition(): Int?

}