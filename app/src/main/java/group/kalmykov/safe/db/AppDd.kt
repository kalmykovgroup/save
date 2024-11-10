package group.kalmykov.safe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import group.kalmykov.safe.dao.SourceDao
import group.kalmykov.safe.entity.Source

@Database(entities = [Source::class], version = 1)
abstract class AppDd : RoomDatabase() {

    abstract fun sourceDao(): SourceDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: AppDd? = null
        fun getInstance(context: Context): AppDd {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDd::class.java,
                        "password_database"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}