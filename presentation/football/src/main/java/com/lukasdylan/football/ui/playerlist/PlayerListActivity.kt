package com.lukasdylan.football.ui.playerlist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.extension.showBottomSheetFragment
import com.lukasdylan.core.widget.GridSpacingItemDecoration
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityPlayerListBinding
import com.lukasdylan.football.ui.playerlist.filter.FilterPlayerFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerListActivity : AppCompatActivity() {

    private val playerListViewModel by viewModel<PlayerListViewModel>()

    private val playerListAdapter by lazy {
        PlayerListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPlayerListBinding>(this, R.layout.activity_player_list)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            rvPlayerList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(GridSpacingItemDecoration(2, 25, true))
                adapter = playerListAdapter
            }
            btnFilter.onClick {
                playerListViewModel.openFilterScreen()
            }
        }
        with(playerListViewModel) {
            observeValue(toolbarTitle) { supportActionBar?.title = "$it Players" }
            observeValue(playerList) {
                playerListAdapter.addData(it)
                playerListAdapter.notifyDataSetChanged()
            }
            observeValue(navigationScreenEvent) {
                if (it.navigationId == NAVIGATE_FILTER_SCREEN) {
                    showBottomSheetFragment(FilterPlayerFragment(), it.params)
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