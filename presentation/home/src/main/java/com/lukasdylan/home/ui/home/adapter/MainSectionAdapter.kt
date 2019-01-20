package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.lukasdylan.core.extension.onAnimateListener
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
        sectionDataList[HomeSectionType.STANDINGS.ordinal] = sectionDataModel
        notifyItemChanged(HomeSectionType.STANDINGS.ordinal)
    }

    fun nextMatchListUpdate(matchList: List<DetailMatch>) {
        val sectionDataModel = HomeSection(HomeSectionType.NEXT_MATCH, matchList, false)
        sectionDataList[HomeSectionType.NEXT_MATCH.ordinal] = sectionDataModel
        notifyItemChanged(HomeSectionType.NEXT_MATCH.ordinal)
    }

    fun previousMatchListUpdate(matchList: List<DetailMatch>) {
        val sectionDataModel = HomeSection(HomeSectionType.PREV_MATCH, matchList, false)
        sectionDataList[HomeSectionType.PREV_MATCH.ordinal] = sectionDataModel
        notifyItemChanged(HomeSectionType.PREV_MATCH.ordinal)
    }

    fun trendingNewsListUpdate(newsList: List<Article>) {
        val sectionDataModel = HomeSection(HomeSectionType.TRENDING_LEAGUE_NEWS, newsList, false)
        sectionDataList[HomeSectionType.TRENDING_LEAGUE_NEWS.ordinal] = sectionDataModel
        notifyItemChanged(HomeSectionType.TRENDING_LEAGUE_NEWS.ordinal)
    }

    fun setImageData(data: Map<String, String>) {
        standingsSectionAdapter.addImageData(data)
        previousMatchSectionAdapter.addImageData(data)
        nextMatchSectionAdapter.addImageData(data)
    }

    fun setLeagueName(leagueName: String) {
        this.leagueName = leagueName
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
            else -> throw (Throwable("Unknown ViewHolder"))
        }
    }

    override fun getItemViewType(position: Int): Int = sectionDataList[position].type.ordinal

    override fun getItemCount(): Int = sectionDataList.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sectionDataModel = sectionDataList[position]
        when (sectionDataModel.type) {
            HomeSectionType.STANDINGS -> {
                val standingsData = sectionDataModel.data as List<Standings>
                (holder as StandingsSectionViewHolder).bind(standingsData, sectionDataModel.isLoading)
            }
            HomeSectionType.PREV_MATCH -> {
                val previousMatchData = sectionDataModel.data as List<DetailMatch>
                (holder as PreviousMatchSectionViewHolder).bind(previousMatchData, sectionDataModel.isLoading)
            }
            HomeSectionType.NEXT_MATCH -> {
                val nextMatchData = sectionDataModel.data as List<DetailMatch>
                (holder as NextMatchSectionViewHolder).bind(nextMatchData, sectionDataModel.isLoading)
            }
            else -> {
                val trendingNewsData = sectionDataModel.data as List<Article>
                (holder as TrendingNewsSectionViewHolder).bind(trendingNewsData, sectionDataModel.isLoading)
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
                    val linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.initialPrefetchItemCount = 6
                    layoutManager = linearLayoutManager
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
                shimmerLayout.onAnimateListener(isLoading)
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
                    isNestedScrollingEnabled = false
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
                shimmerLayout.onAnimateListener(isLoading)
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
                shimmerLayout.onAnimateListener(isLoading)
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
                shimmerLayout.onAnimateListener(isLoading)
                titleTrendingNews.visibility = if (data.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
                rvTrendingNews.adapter = trendingNewsSectionAdapter
                trendingNewsSectionAdapter.addData(data)
                executePendingBindings()
            }
        }
    }

    private fun MutableList<HomeSection>.initialize() {
        add(
            HomeSectionType.TRENDING_LEAGUE_NEWS.ordinal,
            HomeSection(HomeSectionType.TRENDING_LEAGUE_NEWS, emptyList<Article>(), true)
        )
        add(HomeSectionType.PREV_MATCH.ordinal, HomeSection(HomeSectionType.PREV_MATCH, emptyList<DetailMatch>(), true))
        add(HomeSectionType.NEXT_MATCH.ordinal, HomeSection(HomeSectionType.NEXT_MATCH, emptyList<DetailMatch>(), true))
        add(HomeSectionType.STANDINGS.ordinal, HomeSection(HomeSectionType.STANDINGS, emptyList<Standings>(), true))
        notifyItemRangeInserted(0, size)
        toList()
    }

}