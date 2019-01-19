package com.lukasdylan.footballservice.data.service

import com.lukasdylan.footballservice.BuildConfig
import com.lukasdylan.footballservice.data.response.DetailMatchResponse
import com.lukasdylan.footballservice.data.response.DetailTeamResponse
import com.lukasdylan.footballservice.data.response.PlayerResponse
import com.lukasdylan.footballservice.data.response.StandingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

private const val LAST_LEAGUE_EVENT_ENDPOINT = "/eventspastleague.php"
private const val NEXT_LEAGUE_EVENT_ENDPOINT = "/eventsnextleague.php"
private const val TEAM_DETAIL_ENDPOINT = "/lookupteam.php"
private const val LEAGUE_TEAMS_ENDPOINT = "/lookup_all_teams.php"
private const val LEAGUE_STANDINGS_ENDPOINT = "/lookuptable.php"
private const val TEAM_PLAYER_LIST_ENDPOINT = "/lookup_all_players.php"
private const val SEARCH_TEAM_BY_NAME_ENDPOINT = "/searchteams.php"
private const val SEARCH_MATCH_BY_NAME_ENDPOINT = "/searchevents.php"

interface FootballApiServices {
    @GET(BuildConfig.TSDB_API_KEY + LEAGUE_STANDINGS_ENDPOINT)
    fun fetchLeagueStandings(@QueryMap map: HashMap<String, String>): Call<StandingResponse>

    @GET(BuildConfig.TSDB_API_KEY + LAST_LEAGUE_EVENT_ENDPOINT)
    fun fetchPreviousLeagueMatch(@Query("id") id: String): Call<DetailMatchResponse>

    @GET(BuildConfig.TSDB_API_KEY + NEXT_LEAGUE_EVENT_ENDPOINT)
    fun fetchNextLeagueMatch(@Query("id") id: String): Call<DetailMatchResponse>

    @GET(BuildConfig.TSDB_API_KEY + LEAGUE_TEAMS_ENDPOINT)
    fun fetchLeagueTeams(@Query("id") id: String): Call<DetailTeamResponse>

    @GET(BuildConfig.TSDB_API_KEY + TEAM_PLAYER_LIST_ENDPOINT)
    fun fetchPlayersTeam(@Query("id") id: String): Call<PlayerResponse>
}