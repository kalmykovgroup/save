package group.kalmykov.safe.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import group.kalmykov.safe.data.dao.SourceDao
import group.kalmykov.safe.data.entity.Source

@Database(entities = [Source::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sourceDao(): SourceDao

    // реализуем синглтон
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "password_database"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}