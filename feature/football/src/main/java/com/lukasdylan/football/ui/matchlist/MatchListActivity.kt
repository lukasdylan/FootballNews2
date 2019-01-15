package com.lukasdylan.football.ui.matchlist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityMatchListBinding
import com.lukasdylan.football.ui.previousmatch.PreviousMatchActivity
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchListActivity : AppCompatActivity() {

    private val matchListViewModel by viewModel<MatchListViewModel>()

    private val matchListAdapter by lazy {
        MatchListAdapter {
            matchListViewModel.onSelectedMatch(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMatchListBinding>(this, R.layout.activity_match_list)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            return@with rvMatchList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = matchListAdapter
            }
        }
        with(matchListViewModel) {
            observeValue(toolbarTitle) { supportActionBar?.title = it }
            observeValue(matchList) { matchListAdapter.addData(it) }
            observeValue(imageTeamMap) { matchListAdapter.addImageData(it) }
            observeValue(navigationScreenEvent) {
                if (it.navigationId == 0) {
                    startActivity<PreviousMatchActivity>(*it.params.orEmpty())
                }
            }
            intent?.extras?.let { return@with loadData(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}