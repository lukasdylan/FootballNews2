package com.lukasdylan.footballservice.data.database

import androidx.room.*
import com.lukasdylan.footballservice.data.entity.DetailTeam

@Dao
interface DetailTeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteTeam(detailTeam: DetailTeam)

    @Delete
    fun deleteFavoriteTeam(detailTeam: DetailTeam)

    @Query("SELECT * FROM detailteam")
    fun fetchAllFavoriteTeams(): List<DetailTeam>

    @Query("SELECT * FROM detailteam WHERE id_team = :teamId")
    fun checkIsFavoriteTeam(teamId: String): DetailTeam?
}