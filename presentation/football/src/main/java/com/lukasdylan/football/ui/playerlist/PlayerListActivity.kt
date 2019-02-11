package com.lukasdylan.football.ui.playerlist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.extension.showBottomSheetFragment
import com.lukasdylan.core.widget.GridSpacingItemDecoration
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityPlayerListBinding
import com.lukasdylan.football.ui.playerdetail.DetailPlayerActivity
import com.lukasdylan.football.ui.playerlist.filter.FilterPlayerFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerListActivity : AppCompatActivity() {

    private val playerListViewModel by viewModel<PlayerListViewModel>()

    private val playerListAdapter by lazy {
        PlayerListAdapter {
            startActivity<DetailPlayerActivity>("player_data" to it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPlayerListBinding>(this, R.layout.activity_player_list)
        with(binding) {
            lifecycleOwner = this@PlayerListActivity
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            appBarLayout.setLiftable(true)
            rvPlayerList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(GridSpacingItemDecoration(2, 25, true))
                adapter = playerListAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        appBarLayout.setLifted(recyclerView.canScrollVertically(-1))
                    }
                })
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