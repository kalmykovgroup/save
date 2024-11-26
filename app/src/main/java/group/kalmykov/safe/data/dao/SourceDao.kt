package group.kalmykov.safe.data.dao

import androidx.lifecycle.LiveData
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

@Dao
@TypeConverters(Converters::class)
interface SourceDao {

    @Query("SELECT * FROM sources WHERE host LIKE :query ORDER BY created_at DESC")
    fun getItems(query: String): PagingSource<Int, Source>

    @Query("""
        SELECT * FROM sources
        WHERE host LIKE '%' || :query || '%'
        ORDER BY CASE WHEN :sortOrder = 'ASC' THEN updated_at END ASC,
                 CASE WHEN :sortOrder = 'DESC' THEN updated_at END DESC
    """)

    fun getSources(query: String, sortOrder: String): PagingSource<Int, Source>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: Source)

    @Update
    suspend fun update(source: Source)

    @Delete
    suspend fun delete(source: Source)

    @Delete
    suspend fun delete(items: List<Source>)

    @Query("DELETE FROM sources WHERE id IN (:ids)")
    suspend fun deleteItemsByIds(ids: List<Int>)


}