package com.gongracr.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gongracr.persistence.dao.GHProjectsDao
import com.gongracr.persistence.model.project.GHProjectEntity
import com.google.gson.Gson

@Database(entities = [GHProjectEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ghProjectsDao(): GHProjectsDao
}

class Converters {
    @TypeConverter
    fun fromListOfStrings(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun toListOfStrings(jsonList: String): List<String> = Gson().fromJson(jsonList, Array<String>::class.java).toList()

}