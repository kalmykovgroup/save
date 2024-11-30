package group.kalmykov.safe.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.Converters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(Converters::class)
interface SourceDao {

    @Query("SELECT * FROM sources WHERE host LIKE :query ORDER BY updated_at DESC")
    fun all(query: String): Flow<List<Source>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: Source)

    @Update
    suspend fun update(source: Source)

    @Delete
    suspend fun delete(source: Source)

    @Delete
    suspend fun delete(items: List<Source>)


}