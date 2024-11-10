package group.kalmykov.safe.models

import android.database.Cursor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import group.kalmykov.safe.db.SourceContract


@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)


@Entity(tableName = "sources")
class Source {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo("host")
    var host: String? = null

    @ColumnInfo("password")
    var password: String? = null

    @ColumnInfo("username")
    var username : String? = null

    @ColumnInfo("description")
    var description: String? = null

}


