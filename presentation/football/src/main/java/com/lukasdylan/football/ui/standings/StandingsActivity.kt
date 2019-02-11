package com.lukasdylan.football.ui.standings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityStandingsBinding
import com.lukasdylan.football.ui.team.DetailTeamActivity
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsActivity : AppCompatActivity() {

    private val standingsViewModel by viewModel<StandingsViewModel>()

    private val standingsSectionAdapter by lazy {
        StandingsAdapter {
            standingsViewModel.onSelectedTeam(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityStandingsBinding>(this, R.layout.activity_standings)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            appBarLayout.setLiftable(true)
            rvStandings.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = standingsSectionAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        appBarLayout.setLifted(recyclerView.canScrollVertically(-1))
                    }
                })
            }
            lifecycleOwner = this@StandingsActivity
        }
        with(standingsViewModel) {
            observeValue(toolbarTitle) { supportActionBar?.title = it }
            observeValue(standingsList) { standingsSectionAdapter.addData(it) }
            observeValue(imageTeamMap) { standingsSectionAdapter.addImageData(it) }
            observeValue(navigationScreenEvent) {
                startActivity<DetailTeamActivity>(*it.params.orEmpty())
            }
            intent.extras?.let { return@with loadData(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
