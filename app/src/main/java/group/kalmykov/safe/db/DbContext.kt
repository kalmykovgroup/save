package group.kalmykov.safe.db

import android.content.ContentValues
import android.content.Context

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import group.kalmykov.safe.annotation.db.Column
import group.kalmykov.safe.annotation.db.NotMapped
import group.kalmykov.safe.annotation.db.Table
import group.kalmykov.safe.models.DbModel
import group.kalmykov.safe.models.Source
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation


class DbContext(context: Context) {

    private val dbHelper: DbHelper = DbHelper(context)

    val sources: DbSet<Source> = DbSet(dbHelper, Source::class)

}

class DbSet<T : Any>(private val dbHelper: DbHelper, private val clazz: KClass<T>) {

    private var _readableDatabase: SQLiteDatabase? = null;

    private val tableName: String = (clazz.annotations.find { it is Table } as Table).name
    private var projection: Array<String> = arrayOf()

    //Название поля в классе и название поля в базе
    //Если не указать Column аннотаци, то имя будет == имя в базе с учетом реестра.
    private var columns = mapOf<String,  KProperty1<Any, *>>()

    init{

        clazz.members.filterIsInstance<KProperty1<Any, *>>().forEach{property ->

            if(property.hasAnnotation<NotMapped>()){
                return@forEach
            }

            val annotationColumn: Column? = property.findAnnotation<Column>()
            val columnName = annotationColumn?.name ?: property.name

            projection += columnName

            columns += Pair(columnName, property)
        }


    }


    private val readableDatabase: SQLiteDatabase
        get() {
            if(_readableDatabase == null){
                _readableDatabase = dbHelper.readableDatabase
            }
            return _readableDatabase!!
        }


    private var _writableDatabase: SQLiteDatabase? = null;

    private val writableDatabase: SQLiteDatabase
        get(){
            if(_writableDatabase == null){
                _writableDatabase = dbHelper.writableDatabase
            }
            return _writableDatabase!!
        }


    fun item(
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        groupBy: String? = null,
        having: String? = null,
        orderBy: String? = null
    ): T? {

        val cursor = readableDatabase.query(tableName, projection?: this.projection, selection, selectionArgs, groupBy, having, orderBy);

        cursor.use {
            if(cursor.moveToFirst()){
                return createInstance(cursor)
            }
            return null
        }
    }



    fun items(
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        groupBy: String? = null,
        having: String? = null,
        orderBy: String? = null
    ): Flow<T> {

        return flow {

            val cursor = readableDatabase.query(tableName,  projection?: this@DbSet.projection, selection, selectionArgs, groupBy, having, orderBy);

            cursor.use {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        emit(createInstance(cursor))
                        delay(200)

                    } while (cursor.moveToNext())
                }
            }
        }
    }


    private fun createInstance(vararg args: Any?): T {
        return clazz.constructors.firstOrNull { it.parameters.size == args.size }?.call(*args) ?: throw Exception("The constructor with the 'Cursor' parameter was not found")
    }


    fun set(value: T) {

        val values = ContentValues()

        projection.forEach { columnName ->
                if(columnName == "id") return@forEach
            val property: KProperty1<Any, *> = columns[columnName]!!

            values.put(columnName, property.get(value).toString())
        }

        writableDatabase.insert(tableName, null, values)
    }



}