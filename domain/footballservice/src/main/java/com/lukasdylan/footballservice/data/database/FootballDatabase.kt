package com.lukasdylan.footballservice.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lukasdylan.footballservice.data.entity.DetailMatch

@Database(entities = [DetailMatch::class], version = 1, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun detailMatchDao(): DetailMatchDao
}

internal fun initDatabase(application: Application): FootballDatabase {
    return Room.databaseBuilder(application, FootballDatabase::class.java, "Football Main DB").build()
}

internal fun createDetailMatchDao(database: FootballDatabase): DetailMatchDao = database.detailMatchDao()