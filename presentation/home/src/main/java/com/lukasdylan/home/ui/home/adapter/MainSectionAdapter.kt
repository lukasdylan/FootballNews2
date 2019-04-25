package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.lukasdylan.core.extension.onVisibilityListener
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.core.widget.GridSpacingItemDecoration
import com.lukasdylan.core.widget.LinePagerIndicatorDecoration
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.footballservice.data.response.Standings
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.ItemHomeMatchViewBinding
import com.lukasdylan.home.databinding.ItemHomeStandingsViewBinding
import com.lukasdylan.home.databinding.ItemHomeTrendingNewsBinding
import com.lukasdylan.home.model.HomeSection
import com.lukasdylan.home.model.HomeSectionType
import com.lukasdylan.newsservice.data.Article
import org.jetbrains.anko.sdk27.coroutines.onClick

internal const val NAVIGATE_STANDINGS_SCREEN = 0
internal const val NAVIGATE_ALL_NEXT_MATCH_SCREEN = 1
internal const val NAVIGATE_ALL_PREVIOUS_MATCH_SCREEN = 2
internal const val NAVIGATE_DETAIL_NEXT_MATCH_SCREEN = 3
internal const val NAVIGATE_DETAIL_PREVIOUS_MATCH_SCREEN = 4
internal const val NAVIGATE_DETAIL_TEAM_SCREEN = 5
internal const val NAVIGATE_DETAIL_NEWS_SCREEN = 6

class MainSectionAdapter(layoutMaxWidth: Int, private val listener: (NavigationScreen) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sectionDataList = mutableListOf<HomeSection>().apply { initialize() }
    private var leagueName = ""

    private val standingsSectionAdapter by lazy {
        StandingsSectionAdapter {
            val params = arrayOf<Pair<String, Any?>>("team_id" to it)
            val navigationScreen = NavigationScreen(NAVIGATE_DETAIL_TEAM_SCREEN, params)
            listener(navigationScreen)
        }
    }

    private val previousMatchSectionAdapter by lazy {
        PreviousMatchSectionAdapter(layoutMaxWidth) {
            val navigationScreen = NavigationScreen(NAVIGATE_DETAIL_PREVIOUS_MATCH_SCREEN, it)
            listener(navigationScreen)
        }
    }

    private val nextMatchSectionAdapter by lazy {
        NextMatchSectionAdapter(layoutMaxWidth) {
            val navigationScreen = NavigationScreen(NAVIGATE_DETAIL_NEXT_MATCH_SCREEN, it)
            listener(navigationScreen)
        }
    }

    private val trendingNewsSectionAdapter by lazy {
        TrendingNewsSectionAdapter {
            val navigationScreen = NavigationScreen(NAVIGATE_DETAIL_NEWS_SCREEN, it)
            listener(navigationScreen)
        }
    }

    fun standingListUpdate(standingList: List<Standings>) {
        val sectionDataModel = HomeSection(HomeSectionType.STANDINGS, standingList, false)
        update(sectionDataModel)
    }

    fun nextMatchListUpdate(matchList: List<DetailMatch>) {
        val sectionDataModel = HomeSection(HomeSectionType.NEXT_MATCH, matchList, false)
        update(sectionDataModel)
    }

    fun previousMatchListUpdate(matchList: List<DetailMatch>) {
        val sectionDataModel = HomeSection(HomeSectionType.PREV_MATCH, matchList, false)
        update(sectionDataModel)
    }

    fun trendingNewsListUpdate(newsList: List<Article>) {
        val sectionDataModel = HomeSection(HomeSectionType.TRENDING_LEAGUE_NEWS, newsList, false)
        update(sectionDataModel)
    }

    fun setImageData(data: Map<String, String>) {
        standingsSectionAdapter.addImageData(data)
        previousMatchSectionAdapter.addImageData(data)
        nextMatchSectionAdapter.addImageData(data)
    }

    fun setLeagueName(leagueName: String) {
        this.leagueName = leagueName
    }

    private fun update(homeSection: HomeSection) = with(homeSection) {
        sectionDataList[type.ordinal] = this
        notifyItemChanged(type.ordinal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HomeSectionType.TRENDING_LEAGUE_NEWS.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemHomeTrendingNewsBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_trending_news,
                    parent,
                    false
                )
                TrendingNewsSectionViewHolder(binding, trendingNewsSectionAdapter, leagueName)
            }
            HomeSectionType.STANDINGS.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemHomeStandingsViewBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_standings_view,
                    parent,
                    false
                )
                StandingsSectionViewHolder(binding, standingsSectionAdapter, listener)
            }
            HomeSectionType.NEXT_MATCH.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemHomeMatchViewBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_match_view,
                    parent,
                    false
                )
                NextMatchSectionViewHolder(binding, nextMatchSectionAdapter, listener)
            }
            HomeSectionType.PREV_MATCH.ordinal -> {
                val binding = DataBindingUtil.inflate<ItemHomeMatchViewBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_match_view,
                    parent,
                    false
                )
                PreviousMatchSectionViewHolder(binding, previousMatchSectionAdapter, listener)
            }
            else -> throw Throwable("Unknown ViewHolder")
        }
    }

    override fun getItemViewType(position: Int): Int = sectionDataList[position].type.ordinal

    override fun getItemCount(): Int = sectionDataList.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sectionDataModel = sectionDataList[position]
        val data = sectionDataModel.data
        val isLoading = sectionDataModel.isLoading
        when (sectionDataModel.type) {
            HomeSectionType.STANDINGS -> {
                (holder as StandingsSectionViewHolder).bind(data as List<Standings>, isLoading)
            }
            HomeSectionType.PREV_MATCH -> {
                (holder as PreviousMatchSectionViewHolder).bind(data as List<DetailMatch>, isLoading)
            }
            HomeSectionType.NEXT_MATCH -> {
                (holder as NextMatchSectionViewHolder).bind(data as List<DetailMatch>, isLoading)
            }
            HomeSectionType.TRENDING_LEAGUE_NEWS -> {
                (holder as TrendingNewsSectionViewHolder).bind(data as List<Article>, isLoading)
            }
        }
    }

    class StandingsSectionViewHolder(
        private val binding: ItemHomeStandingsViewBinding,
        private val standingsSectionAdapter: StandingsSectionAdapter,
        private val listener: (NavigationScreen) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                rvStandings.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context).also {
                        it.initialPrefetchItemCount = 6
                    }
                    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                }
                tvSeeAllStandings.onClick {
                    val navigationScreen = NavigationScreen(NAVIGATE_STANDINGS_SCREEN)
                    listener(navigationScreen)
                }
            }
        }

        fun bind(data: List<Standings>, isLoading: Boolean) {
            with(binding) {
                tvSeeAllStandings.visibility = if (data.isNullOrEmpty()) View.GONE else View.VISIBLE
                shimmerLayout.onVisibilityListener(isLoading)
                rvStandings.adapter = standingsSectionAdapter
                standingsSectionAdapter.addData(data)
                executePendingBindings()
            }
        }
    }

    class PreviousMatchSectionViewHolder(
        private val binding: ItemHomeMatchViewBinding,
        private val previousMatchSectionAdapter: PreviousMatchSectionAdapter,
        private val listener: (NavigationScreen) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                rvHorizontalMatchList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val snapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(this)
                    addItemDecoration(GridSpacingItemDecoration(6, 50, true))
                }
                tvSeeAllMatch.onClick {
                    val navigationScreen = NavigationScreen(NAVIGATE_ALL_PREVIOUS_MATCH_SCREEN)
                    listener(navigationScreen)
                }
                this.title = itemView.resources.getString(R.string.title_previous_match_home)
            }
        }

        fun bind(data: List<DetailMatch>, isLoading: Boolean) {
            with(binding) {
                tvSeeAllMatch.visibility = if (data.isNullOrEmpty()) View.GONE else View.VISIBLE
                shimmerLayout.onVisibilityListener(isLoading)
                rvHorizontalMatchList.adapter = previousMatchSectionAdapter
                previousMatchSectionAdapter.addData(data)
                executePendingBindings()
            }
        }
    }

    class NextMatchSectionViewHolder(
        private val binding: ItemHomeMatchViewBinding,
        private val nextMatchSectionAdapter: NextMatchSectionAdapter,
        private val listener: (NavigationScreen) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                rvHorizontalMatchList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val snapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(this)
                    isNestedScrollingEnabled = false
                    addItemDecoration(GridSpacingItemDecoration(6, 50, true))
                }
                tvSeeAllMatch.onClick {
                    val navigationScreen = NavigationScreen(NAVIGATE_ALL_NEXT_MATCH_SCREEN)
                    listener(navigationScreen)
                }
                this.title = itemView.resources.getString(R.string.title_next_match_home)
            }
        }

        fun bind(data: List<DetailMatch>, isLoading: Boolean) {
            with(binding) {
                tvSeeAllMatch.visibility = if (data.isNullOrEmpty()) View.GONE else View.VISIBLE
                shimmerLayout.onVisibilityListener(isLoading)
                rvHorizontalMatchList.adapter = nextMatchSectionAdapter
                nextMatchSectionAdapter.addData(data)
                executePendingBindings()
            }
        }
    }

    class TrendingNewsSectionViewHolder(
        private val binding: ItemHomeTrendingNewsBinding,
        private val trendingNewsSectionAdapter: TrendingNewsSectionAdapter,
        private val leagueName: String
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                rvTrendingNews.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val snapHelper = PagerSnapHelper()
                    snapHelper.attachToRecyclerView(this)
                    isNestedScrollingEnabled = false
                    addItemDecoration(LinePagerIndicatorDecoration())
                }
            }
        }

        fun bind(data: List<Article>, isLoading: Boolean) {
            with(binding) {
                this.title = itemView.resources.getString(R.string.title_news_home, leagueName)
                shimmerLayout.onVisibilityListener(isLoading)
                titleTrendingNews.visibility = if (data.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
                rvTrendingNews.adapter = trendingNewsSectionAdapter
                trendingNewsSectionAdapter.addData(data)
                executePendingBindings()
            }
        }
    }
}

private fun MutableList<HomeSection>.initialize() {
    HomeSectionType.values().forEach {
        add(it.ordinal, HomeSection(it, emptyList<Any>(), true))
    }
}