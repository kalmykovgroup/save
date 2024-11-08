package group.kalmykov.safe.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQuery
import android.provider.BaseColumns
import group.kalmykov.safe.models.Source
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SourceContract(override val dbHelper: DbHelper): IDbContract<Source> {



    companion object{

        const val TABLE_NAME = "sources"

        const val SQL_CREATE_ENTRIES_SOURCE: String  =
            "CREATE TABLE $TABLE_NAME (" +
                    "${Column.ID} INTEGER PRIMARY KEY," +
                    "${Column.HOST} TEXT," +
                    "${Column.USERNAME} TEXT," +
                    "${Column.PASSWORD} TEXT," +
                    "${Column.DESCRIPTION} TEXT)"

        const val SQL_DELETE_ENTRIES_SOURCE = "DROP TABLE IF EXISTS $TABLE_NAME"

    }

    object Column : BaseColumns {
        const val ID = "id"
        const val HOST = "host"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val DESCRIPTION = "description"
    }

    private var readableDatabase: SQLiteDatabase? = null;

    override fun getReadableDatabase(): SQLiteDatabase{
        if(readableDatabase == null){
            readableDatabase = dbHelper.readableDatabase
        }
       return readableDatabase!!
    }

    private var writableDatabase: SQLiteDatabase? = null;

    override fun getWritableDatabase(): SQLiteDatabase{
        if(writableDatabase == null){
            writableDatabase = dbHelper.writableDatabase
        }
       return writableDatabase!!
    }

    override val projection = arrayOf(
            Column.ID,
            Column.HOST,
            Column.USERNAME,
            Column.PASSWORD,
            Column.DESCRIPTION,
    )

    override fun item(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Source? {

        val cursor = getReadableDatabase().query(TABLE_NAME, projection?: this.projection, selection, selectionArgs, groupBy, having, orderBy);

        cursor.use {
            if(cursor.moveToFirst())
                return Source(cursor)
            return null
        }
    }

    override fun items(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Flow<Source> {

        return flow {
            val cursor = getReadableDatabase().query(TABLE_NAME,  projection?: this@SourceContract.projection, selection, selectionArgs, groupBy, having, orderBy);

            cursor.use {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        emit(Source(cursor))
                        delay(200)

                    } while (cursor.moveToNext())
                }
            }
        }
    }


    override fun set(value: Source) {
        val values = ContentValues().apply {
            put(Column.HOST, value.host)
            put(Column.USERNAME, value.username)
            put(Column.PASSWORD, value.password)
            put(Column.DESCRIPTION, value.description)
        }

        getWritableDatabase().insert(TABLE_NAME, null, values)
    }

}

