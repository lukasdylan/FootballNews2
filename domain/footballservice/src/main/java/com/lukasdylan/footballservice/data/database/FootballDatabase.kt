package com.lukasdylan.footballservice.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.footballservice.data.entity.DetailTeam

@Database(entities = [DetailMatch::class, DetailTeam::class], version = 2, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun detailMatchDao(): DetailMatchDao
    abstract fun detailTeamDao(): DetailTeamDao
}

internal fun initDatabase(application: Application): FootballDatabase {
    return Room.databaseBuilder(application, FootballDatabase::class.java, "Football Main DB")
        .addMigrations(migrationVersion1to2)
        .build()
}

internal fun createDetailMatchDao(database: FootballDatabase): DetailMatchDao = database.detailMatchDao()

internal fun createDetailTeamDao(database: FootballDatabase): DetailTeamDao = database.detailTeamDao()

private val migrationVersion1to2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS 'detailteam' ('id_team' TEXT NOT NULL, 'sport_type' TEXT, 'team_name' TEXT, 'league_name' TEXT, 'formed_year' TEXT, 'team_manager' TEXT, 'team_stadium' TEXT, 'team_badge' TEXT, 'team_stadium_image' TEXT, 'team_stadium_desc' TEXT, 'team_desc' TEXT, 'team_stadium_location' TEXT, 'team_stadium_capacity' TEXT, 'team_website' TEXT, 'team_facebook_page' TEXT, 'team_twitter_page' TEXT, 'team_instagram_page' TEXT, 'team_fan_art_1' TEXT, 'team_fan_art_2' TEXT, 'team_fan_art_3' TEXT, 'team_fan_art_4' TEXT, PRIMARY KEY('id_team'))")
    }
}