package com.lukasdylan.home.ui.home

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.lukasdylan.core.extension.*
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.footballservice.data.model.LA_LIGA_ID
import com.lukasdylan.footballservice.data.model.PREMIER_LEAGUE_ID
import com.lukasdylan.footballservice.data.model.SERIE_A_ID
import com.lukasdylan.home.R
import com.lukasdylan.home.asyncText
import com.lukasdylan.home.databinding.ActivityHomeBinding
import com.lukasdylan.home.ui.home.adapter.*
import com.lukasdylan.home.ui.leaguelist.LeagueListFragment
import com.lukasdylan.home.ui.nextmatch.NextMatchFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.lukasdylan.footballservice.R as R2
import com.lukasdylan.newsservice.R as R3

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    private val layoutMaxWidth: Int by lazy {
        val displayMetrics = DisplayMetrics().also {
            windowManager.defaultDisplay.getMetrics(it)
        }
        return@lazy displayMetrics.widthPixels * 3 / 4
    }

    private val adapterListener: (NavigationScreen) -> Unit = {
        when (it.navigationId) {
            NAVIGATE_DETAIL_NEXT_MATCH_SCREEN -> {
                showBottomSheetFragment(NextMatchFragment(), it.params)
            }
            NAVIGATE_DETAIL_PREVIOUS_MATCH_SCREEN -> {
                val scheme = resources.getString(R2.string.scheme)
                val host = resources.getString(R2.string.deep_link_detail_previous_match)
                val bundle = bundleOf(*it.params.orEmpty())
                openDeepLinkActivity(scheme, host, bundle)
            }
            NAVIGATE_STANDINGS_SCREEN -> {
                homeViewModel.openStandingsScreen()
            }
            NAVIGATE_DETAIL_NEWS_SCREEN -> {
                val scheme = resources.getString(R3.string.scheme)
                val host = resources.getString(R3.string.deep_link_detail_news)
                val bundle = bundleOf(*it.params.orEmpty())
                openDeepLinkActivity(scheme, host, bundle)
            }
            NAVIGATE_ALL_PREVIOUS_MATCH_SCREEN -> {
                homeViewModel.openAllMatchListScreen(0)
            }
            NAVIGATE_DETAIL_TEAM_SCREEN -> {
                val teamId = it.params?.get(0)?.second as? String
                homeViewModel.openDetailTeamScreen(teamId.orEmpty())
            }
        }
    }

    private var mainSectionAdapter: MainSectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        with(binding) {
            lifecycleOwner = this@HomeActivity
            setSupportActionBar(toolbar)
            supportActionBar?.title = "Football News 2"
            rvMain.apply {
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(this@HomeActivity)
                mainSectionAdapter = MainSectionAdapter(layoutMaxWidth, adapterListener)
                adapter = mainSectionAdapter
            }
            cvLeagueSelection.onClick {
                showBottomSheetFragment(LeagueListFragment())
            }
            vm = homeViewModel
        }
        with(homeViewModel) {
            observeValue(imageTeamMap) { mainSectionAdapter?.setImageData(it) }
            observeValue(standingsList) { mainSectionAdapter?.standingListUpdate(it) }
            observeValue(previousMatchList) { mainSectionAdapter?.previousMatchListUpdate(it) }
            observeValue(nextMatchList) { mainSectionAdapter?.nextMatchListUpdate(it) }
            observeValue(leagueTrendingNewsList) { mainSectionAdapter?.trendingNewsListUpdate(it) }
            observeValue(selectedLeague) {
                TransitionManager.beginDelayedTransition(binding.cvLeagueSelection)
                binding.ivLeagueIcon.loadImage(
                    when (it.leagueId) {
                        PREMIER_LEAGUE_ID.toString() -> R.drawable.icon_premier_league
                        LA_LIGA_ID.toString() -> R.drawable.icon_la_liga
                        SERIE_A_ID.toString() -> R.drawable.icon_serie_a
                        else -> R.drawable.no_image_icon
                    }
                )
                binding.tvLeagueName.asyncText(it.leagueName)
                mainSectionAdapter?.setLeagueName(it.leagueName)
            }
            observeValue(errorSnackBarEvent) {
                binding.rootLayout.showErrorSnackBar(it.getMessage(this@HomeActivity))
            }
            observeValue(navigationScreenEvent) {
                when (it.navigationId) {
                    NAVIGATE_STANDINGS_SCREEN -> {
                        val scheme = resources.getString(R2.string.scheme)
                        val host = resources.getString(R2.string.deep_link_standings)
                        val bundle = bundleOf(*it.params.orEmpty())
                        openDeepLinkActivity(scheme, host, bundle)
                    }
                    NAVIGATE_ALL_PREVIOUS_MATCH_SCREEN -> {
                        val scheme = resources.getString(R2.string.scheme)
                        val host = resources.getString(R2.string.deep_link_match_list)
                        val bundle = bundleOf(*it.params.orEmpty())
                        openDeepLinkActivity(scheme, host, bundle)
                    }
                    NAVIGATE_DETAIL_TEAM_SCREEN -> {
                        val scheme = resources.getString(R2.string.scheme)
                        val host = resources.getString(R2.string.deep_link_detail_team)
                        val bundle = bundleOf(*it.params.orEmpty())
                        openDeepLinkActivity(scheme, host, bundle)
                    }
                }
            }
            observeNoValue(reloadHomeAdapterEvent) {
                binding.rvMain.adapter = null
                mainSectionAdapter = MainSectionAdapter(layoutMaxWidth, adapterListener)
                binding.rvMain.adapter = mainSectionAdapter
                loadHomeView()
            }

            loadHomeView()
        }
    }
}