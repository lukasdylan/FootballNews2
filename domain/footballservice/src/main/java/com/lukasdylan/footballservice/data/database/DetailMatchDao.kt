package com.lukasdylan.footballservice.data.database

import androidx.room.*
import com.lukasdylan.footballservice.data.entity.DetailMatch

@Dao
interface DetailMatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteMatch(detailMatch: DetailMatch)

    @Delete
    fun deleteFavoriteMatch(detailMatch: DetailMatch)

    @Query("SELECT * FROM detailmatch")
    fun fetchAllFavoriteMatches(): List<DetailMatch>

    @Query("SELECT * FROM detailmatch WHERE id_event = :matchId")
    fun checkIsFavoriteMatch(matchId: String): DetailMatch?
}