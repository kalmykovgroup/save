package group.kalmykov.safe.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQuery
import group.kalmykov.safe.models.Source
import kotlinx.coroutines.flow.Flow

interface IDbContract<T> {

    val dbHelper: DbHelper


    val projection: Array<String>

    fun getReadableDatabase(): SQLiteDatabase

    fun getWritableDatabase(): SQLiteDatabase

    fun item(
             projection: Array<String>? = null,
             selection: String? = null,
             selectionArgs: Array<String>? = null,
             groupBy: String? = null,
             having: String? = null,
             orderBy: String? = null): T?

    fun items(
              projection: Array<String>? = null,
              selection: String? = null,
              selectionArgs: Array<String>? = null,
              groupBy: String? = null,
              having: String? = null,
              orderBy: String? = null) : Flow<T>

    fun set(value: T)





}