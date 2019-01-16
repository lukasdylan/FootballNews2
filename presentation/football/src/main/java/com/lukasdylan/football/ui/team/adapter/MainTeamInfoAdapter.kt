package com.lukasdylan.football.ui.team.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.core.extension.onAnimateListener
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.core.widget.GridSpacingItemDecoration
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemTeamHeaderBinding
import com.lukasdylan.football.databinding.ItemTeamInfoBinding
import com.lukasdylan.football.databinding.ItemTeamPlayersBinding
import com.lukasdylan.football.model.TeamInfoDataModel
import com.lukasdylan.football.model.TeamInfoType
import com.lukasdylan.football.utility.asyncText
import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.lukasdylan.footballservice.data.response.Player
import org.jetbrains.anko.sdk27.coroutines.onClick

internal const val NAVIGATE_BROWSER = 0
internal const val NAVIGATE_DETAIL_PLAYER_SCREEN = 1
internal const val NAVIGATE_ALL_PLAYER_SCREEN = 2

class MainTeamInfoAdapter(private val listener: (NavigationScreen) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val teamInfoList = mutableListOf<TeamInfoDataModel>().apply { initialize() }

    private val teamPlayersAdapter by lazy {
        HorizontalTeamPlayersAdapter {
            val params = arrayOf<Pair<String, Any?>>("detail_player" to it)
            listener(NavigationScreen(NAVIGATE_DETAIL_PLAYER_SCREEN, params))
        }
    }

    fun updateTeamInfo(detailTeam: DetailTeam) {
        val teamInfo = TeamInfoDataModel(TeamInfoType.TEAM_INFO, listOf(detailTeam), false)
        teamInfoList[TeamInfoType.TEAM_INFO.ordinal] = teamInfo
        notifyItemChanged(TeamInfoType.TEAM_INFO.ordinal)

        val stadiumInfo = TeamInfoDataModel(TeamInfoType.STADIUM_INFO, listOf(detailTeam), false)
        teamInfoList[TeamInfoType.STADIUM_INFO.ordinal] = stadiumInfo
        notifyItemChanged(TeamInfoType.STADIUM_INFO.ordinal)
    }

    fun updateTeamPlayers(data: List<Player>) {
        val teamInfo = TeamInfoDataModel(TeamInfoType.PLAYER_INFO, data, false)
        teamInfoList[TeamInfoType.PLAYER_INFO.ordinal] = teamInfo
        notifyItemChanged(TeamInfoType.PLAYER_INFO.ordinal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TeamInfoType.TEAM_INFO.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemTeamHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_team_header,
                    parent,
                    false
                )
                TeamHeaderViewHolder(binding, listener)
            }
            TeamInfoType.PLAYER_INFO.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemTeamPlayersBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_team_players,
                    parent,
                    false
                )
                TeamPlayerViewHolder(binding, teamPlayersAdapter, listener)
            }
            TeamInfoType.STADIUM_INFO.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemTeamInfoBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_team_info,
                    parent,
                    false
                )
                TeamInfoViewHolder(binding)
            }
            else -> throw (Throwable("Unknown ViewHolder"))
        }
    }

    override fun getItemCount(): Int = teamInfoList.size

    override fun getItemViewType(position: Int): Int = teamInfoList[position].type.ordinal

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val teamInfo = teamInfoList[position]
        when (teamInfo.type) {
            TeamInfoType.TEAM_INFO -> {
                val data = teamInfo.data as List<DetailTeam>
                (holder as TeamHeaderViewHolder).bind(data[0])
            }
            TeamInfoType.PLAYER_INFO -> {
                val data = teamInfo.data as List<Player>
                (holder as TeamPlayerViewHolder).bind(data, teamInfo.isLoading)
            }
            TeamInfoType.STADIUM_INFO -> {
                val data = teamInfo.data as List<DetailTeam>
                (holder as TeamInfoViewHolder).bind(data[0])
            }
            else -> {

            }
        }
    }

    private fun MutableList<TeamInfoDataModel>.initialize() {
        add(TeamInfoDataModel(TeamInfoType.TEAM_INFO, emptyList<DetailTeam>(), true))
        add(TeamInfoDataModel(TeamInfoType.STADIUM_INFO, emptyList<DetailTeam>(), true))
        add(TeamInfoDataModel(TeamInfoType.PLAYER_INFO, emptyList<Player>(), true))
//        add(TeamInfoDataModel(TeamInfoType.MANAGER_INFO, emptyList<String>(), true))
//        add(TeamInfoDataModel(TeamInfoType.NEWS_INFO, emptyList<Article>(), true))
        notifyItemRangeInserted(0, size)
        toList()
    }

    class TeamHeaderViewHolder(
        private val binding: ItemTeamHeaderBinding,
        private val listener: (NavigationScreen) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(detailTeam: DetailTeam) {
            with(binding) {
                ivTeamIcon.loadImageByUrl(detailTeam.teamBadge)
                tvTeamName.asyncText(detailTeam.teamName)
                tvLeagueName.asyncText(detailTeam.leagueName)
                chipFacebook.apply {
                    visibility = if (detailTeam.teamFacebookPage.isNullOrBlank()) View.GONE else View.VISIBLE
                    onClick {
                        val params = arrayOf<Pair<String, Any?>>("url" to detailTeam.teamFacebookPage)
                        listener.invoke(NavigationScreen(NAVIGATE_BROWSER, params))
                    }
                }
                chipTwitter.apply {
                    visibility = if (detailTeam.teamTwitterPage.isNullOrBlank()) View.GONE else View.VISIBLE
                    onClick {
                        val params = arrayOf<Pair<String, Any?>>("url" to detailTeam.teamTwitterPage)
                        listener.invoke(NavigationScreen(NAVIGATE_BROWSER, params))
                    }
                }
                chipInstagram.apply {
                    visibility = if (detailTeam.teamInstagramPage.isNullOrBlank()) View.GONE else View.VISIBLE
                    onClick {
                        val params = arrayOf<Pair<String, Any?>>("url" to detailTeam.teamInstagramPage)
                        listener.invoke(NavigationScreen(NAVIGATE_BROWSER, params))
                    }
                }
                chipOfficialWebsite.apply {
                    visibility = if (detailTeam.teamWebsite.isNullOrBlank()) View.GONE else View.VISIBLE
                    onClick {
                        val params = arrayOf<Pair<String, Any?>>("url" to detailTeam.teamWebsite)
                        listener.invoke(NavigationScreen(NAVIGATE_BROWSER, params))
                    }
                }
                executePendingBindings()
            }
        }
    }

    class TeamInfoViewHolder(private val binding: ItemTeamInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        private var isExpanded = false

        init {
            with(binding) {
                tvShowMore.onClick {
                    if(isExpanded) {
                        tvInfoDesc.maxLines = 3
                        tvShowMore.text = "Show More"
                    } else {
                        tvInfoDesc.maxLines = Integer.MAX_VALUE
                        tvShowMore.text = "Show Less"
                    }
                    TransitionManager.beginDelayedTransition(cvRoot)
                    isExpanded = !isExpanded
                }
            }
        }

        fun bind(detailTeam: DetailTeam) {
            with(binding) {
                titleInfo.asyncText("About ${detailTeam.teamStadium.orEmpty()}")
                ivPhoto.loadImageByUrl(detailTeam.teamStadiumImage)
                tvInfoDesc.asyncText(detailTeam.teamStadiumDesc)
                executePendingBindings()
            }
        }
    }

    class TeamPlayerViewHolder(private val binding: ItemTeamPlayersBinding,
                               private val teamPlayersAdapter: HorizontalTeamPlayersAdapter,
                               private val listener: (NavigationScreen) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                rvPlayers.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    isNestedScrollingEnabled = false
                    addItemDecoration(GridSpacingItemDecoration(10, 50, true))
                }
                tvSeeAllPlayer.onClick {
                    listener.invoke(NavigationScreen(NAVIGATE_ALL_PLAYER_SCREEN))
                }
            }
        }

        fun bind(data: List<Player>, isLoading: Boolean) {
            with(binding) {
                tvSeeAllPlayer.visibility = if (data.isNullOrEmpty()) View.GONE else View.VISIBLE
                shimmerLayout.onAnimateListener(isLoading)
                rvPlayers.adapter = teamPlayersAdapter
                teamPlayersAdapter.addData(data)
                executePendingBindings()
            }
        }
    }

}