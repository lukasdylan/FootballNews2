package com.lukasdylan.football.ui.team.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.footballservice.data.response.DetailTeam
import com.lukasdylan.footballservice.data.response.Player
import com.lukasdylan.football.model.TeamInfoDataModel
import com.lukasdylan.football.model.TeamInfoType
import com.lukasdylan.newsservice.data.Article

class MainTeamInfoAdapter(
    layoutMaxWidth: Int, private val listener: (NavigationScreen) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val teamInfoList = mutableListOf<TeamInfoDataModel>().apply { initialize() }

//    private val previousMatchSectionAdapter by lazy {
//        PreviousMatchSectionAdapter(layoutMaxWidth) {
//            val navigationScreen = NavigationScreen(NAVIGATE_DETAIL_PREVIOUS_MATCH_SCREEN, it)
//            listener(navigationScreen)
//        }
//    }
//
//    private val nextMatchSectionAdapter by lazy {
//        NextMatchSectionAdapter(layoutMaxWidth) {
//            val navigationScreen = NavigationScreen(NAVIGATE_DETAIL_NEXT_MATCH_SCREEN, it)
//            listener(navigationScreen)
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val pool = RecyclerView.RecycledViewPool()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = teamInfoList.size

    override fun getItemViewType(position: Int): Int = teamInfoList[position].type.ordinal

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun MutableList<TeamInfoDataModel>.initialize() {
        add(TeamInfoDataModel(TeamInfoType.TEAM_INFO, emptyList<DetailTeam>(), true))
        add(TeamInfoDataModel(TeamInfoType.STADIUM_INFO, emptyList<DetailTeam>(), true))
        add(TeamInfoDataModel(TeamInfoType.PLAYER_INFO, emptyList<Player>(), true))
        add(TeamInfoDataModel(TeamInfoType.MANAGER_INFO, emptyList<String>(), true))
        add(TeamInfoDataModel(TeamInfoType.NEWS_INFO, emptyList<Article>(), true))
        notifyItemRangeInserted(0, size)
    }
}