package group.kalmykov.safe.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import group.kalmykov.safe.entity.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Query("SELECT * FROM sources")
    fun all(): Flow<List<Source>>


  /*  @Query("SELECT * FROM sources WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Source>

    @Query("SELECT * FROM sources WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Source
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg sources: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: Source)

    @Update
    suspend fun update(source: Source)

    @Delete
    suspend fun delete(source: Source)

    @Query("DELETE FROM sources WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM sources")
    suspend fun deleteAll()

}