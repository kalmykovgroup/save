package group.kalmykov.safe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sources")
class Source {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo("host")
    var host: String = ""

    @ColumnInfo("password")
    var password: String = ""

    @ColumnInfo("username")
    var username : String? = null

    @ColumnInfo("description")
    var description: String? = null

    @ColumnInfo("created_at")
    var created_at: Long = System.currentTimeMillis()

    @ColumnInfo("updated_at")
    var updated_at: Long = System.currentTimeMillis()

    constructor() {}

    constructor(description: String?, username: String?, password: String, host: String) {
        this.description = description
        this.username = username
        this.password = password
        this.host = host
    }
}


