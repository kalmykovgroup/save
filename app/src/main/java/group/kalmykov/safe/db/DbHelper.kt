package group.kalmykov.safe.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SourceContract.SQL_CREATE_ENTRIES_SOURCE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Эта база данных является всего лишь кэшем для оперативных данных, поэтому политика ее обновления
        // заключается в том, чтобы просто удалить данные и начать все сначала
        db.execSQL(SourceContract.SQL_DELETE_ENTRIES_SOURCE)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // Если вы изменяете схему базы данных, вы должны увеличить версию базы данных.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "save.db"
    }
}